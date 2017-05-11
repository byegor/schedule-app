package com.egor.pulse.app.match;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.schedule.shared.bean.Match;
import com.egor.pulse.app.R;
import com.egor.pulse.app.services.ServiceGenerator;
import com.egor.pulse.app.task.MatchTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Egor on 22.06.2016.
 */
//todo wrap all in try catch
public class MatchWrapperFragment extends Fragment {

    public SwipeRefreshLayout swipeContainer;
    private MatchInfoFragment infoFragment;
    private MatchNetWorthFragment netWorthFragment;
    private MatchPlayerFragment radiantFragment;
    private MatchPlayerFragment direFragment;

    private long matchId = -1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeMatchContainer);
        swipeContainer.setRefreshing(true);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<Match> games = ServiceGenerator.getGameService().getMatchById(getMatchId());
                games.enqueue(getCallBack());
            }
        });
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        infoFragment = MatchInfoFragment.newInstance(null);
        transaction.add(R.id.team_info, infoFragment, "team_info_frag");
        transaction.commit();
        new MatchTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.getArguments().getInt("gameId"), this.getArguments().getInt("gameNumber"));
    }

    private long getMatchId() {
        return matchId;
    }

    public static MatchWrapperFragment newInstance(int gameId, int gameNumber) {
        MatchWrapperFragment fragment = new MatchWrapperFragment();
        Bundle args = new Bundle();
        args.putInt("gameId", gameId);
        args.putInt("gameNumber", gameNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_wrapper, parent, false);
    }

    private Callback<Match> getCallBack() {
        return new Callback<Match>() {
            @Override
            public void onResponse(Call<Match> call, Response<Match> response) {
                Match match = response.body();
                refresh(match);
            }

            @Override
            public void onFailure(Call<Match> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                Log.e("API", "couldn't get match", t);
            }
        };
    }

    public void refresh(Match match) {
        matchId = match.getMatchId();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        infoFragment = MatchInfoFragment.newInstance(match);
        radiantFragment = MatchPlayerFragment.newInstance(match.getRadiantTeam(), true);
        direFragment = MatchPlayerFragment.newInstance(match.getDireTeam(), false);
        if (match.getNetworth() != null && match.getNetworth().size() > 1) {
            netWorthFragment = MatchNetWorthFragment.newInstance(match.getNetworth(), match.getDuration());
            transaction.replace(R.id.networth_graph, netWorthFragment, "net_worth_frag");
        }
        transaction.replace(R.id.team_info, infoFragment, "team_info_frag");
        transaction.replace(R.id.radiant_info_frag, radiantFragment, "radiant_info_frag");
        transaction.replace(R.id.dire_info_frag, direFragment, "dire_info_frag");
        transaction.commit();
        swipeContainer.setRefreshing(false);
        swipeContainer.setEnabled(match.getMatchStatus() == 0);
    }

    public void failedToGetData() {
        swipeContainer.setRefreshing(false);
        swipeContainer.setEnabled(true);
    }
}
