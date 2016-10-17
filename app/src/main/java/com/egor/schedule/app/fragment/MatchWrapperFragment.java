package com.egor.schedule.app.fragment;

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

import com.eb.schedule.shared.bean.GameBean;
import com.eb.schedule.shared.bean.Match;
import com.egor.schedule.app.R;
import com.egor.schedule.app.adapter.schedule.ListAddapterItem;
import com.egor.schedule.app.adapter.schedule.ListHeader;
import com.egor.schedule.app.adapter.schedule.ListItem;
import com.egor.schedule.app.services.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Egor on 22.06.2016.
 */
//todo refresh on swipe
//todo series type
//todo add winner
//todo wrap all in try catch
public class MatchWrapperFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    private MatchInfoFragment infoFragment;
    private MatchPlayeFragment radiantFragment;
    private MatchPlayeFragment direFragment;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        final Match match = (Match) arguments.get("match");


        if (match != null) {
            swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeMatchContainer);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Call<Match> games = ServiceGenerator.getGameService().getMatchById(match.getMatchId());
                    games.enqueue(getCallBack());
                }
            });


            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            infoFragment = MatchInfoFragment.newInstance(match);
            radiantFragment = MatchPlayeFragment.newInstance(match.getRadiantTeam(), true);
            direFragment = MatchPlayeFragment.newInstance(match.getDireTeam(), false);
            transaction.add(R.id.team_info, infoFragment, "team_info_frag");
            transaction.add(R.id.radiant_info_frag, radiantFragment, "radiant_info_frag");
            transaction.add(R.id.dire_info_frag, direFragment, "dire_info_frag");
            transaction.commit();
        }
    }

    public static MatchWrapperFragment newInstance(Match match) {
        MatchWrapperFragment fragment = new MatchWrapperFragment();
        Bundle args = new Bundle();
        args.putSerializable("match", match);
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
                Match body = response.body();
                infoFragment.setMatch(body);
                direFragment.setTeam(body.getDireTeam());
                radiantFragment.setTeam(body.getRadiantTeam());
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Match> call, Throwable t) {
                Log.e("API", "couldn't get match", t);
            }
        };
    }
}
