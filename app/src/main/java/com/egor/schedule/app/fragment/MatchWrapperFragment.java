package com.egor.schedule.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eb.schedule.shared.bean.Match;
import com.egor.schedule.app.R;

/**
 * Created by Egor on 22.06.2016.
 */
public class MatchWrapperFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        Match match = (Match) arguments.get("match");
        if(match != null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.team_info, MatchInfoFragment.newInstance(match), "team_info_frag");
            transaction.add(R.id.radiant_info_frag, MatchPlayeFragment.newInstance(match.getRadiantTeam(), true), "radiant_info_frag");
            transaction.add(R.id.dire_info_frag, MatchPlayeFragment.newInstance(match.getDireTeam(), false), "dire_info_frag");
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
}
