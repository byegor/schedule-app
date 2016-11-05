package com.egor.schedule.app.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.eb.schedule.shared.bean.Player;
import com.eb.schedule.shared.bean.TeamBean;
import com.egor.schedule.app.R;
import com.egor.schedule.app.adapter.PlayerAdapter;

import java.util.List;

/**
 * Created by Egor on 02.07.2016.
 */
public class MatchPlayeFragment extends Fragment {

    private TeamBean team;
    private Boolean radiant;
    PlayerAdapter adapter;

    public static MatchPlayeFragment newInstance(TeamBean teamBean, boolean radiant) {
        MatchPlayeFragment fragment = new MatchPlayeFragment();
        Bundle args = new Bundle();
        args.putSerializable("team", teamBean);
        args.putSerializable("radiant", radiant);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.team = (TeamBean) getArguments().getSerializable("team");
        this.radiant = (Boolean) getArguments().getSerializable("radiant");
        adapter = new PlayerAdapter(this.getActivity());
        adapter.addAll(team.getPlayers());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (radiant) {
            view.findViewById(R.id.team_panel).setBackgroundColor(Color.parseColor("#64bd5f"));
        } else {
            view.findViewById(R.id.team_panel).setBackgroundColor(Color.RED);
        }

        TextView teamName = (TextView) view.findViewById(R.id.team_name);
        teamName.setText(team.getName());
        final ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
    }


}
