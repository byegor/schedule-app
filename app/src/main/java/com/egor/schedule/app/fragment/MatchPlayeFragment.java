package com.egor.schedule.app.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.eb.schedule.shared.bean.HeroBean;
import com.eb.schedule.shared.bean.LeagueBean;
import com.eb.schedule.shared.bean.Match;
import com.eb.schedule.shared.bean.Player;
import com.eb.schedule.shared.bean.TeamBean;
import com.egor.schedule.app.R;
import com.egor.schedule.app.adapter.PlayerAdapter;
import com.egor.schedule.app.adapter.ScheduleAdapter;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import static com.egor.schedule.app.utils.ImageUtils.BASE_URL;

/**
 * Created by Egor on 02.07.2016.
 */
public class MatchPlayeFragment extends Fragment {

    private List<Player> players;
    PlayerAdapter adapter;

    public static MatchPlayeFragment newInstance(List<Player> players) {
        MatchPlayeFragment fragment = new MatchPlayeFragment();
        Bundle args = new Bundle();
        args.putSerializable("players", (Serializable) players);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.players = (List<Player>) getArguments().getSerializable("players");
        adapter = new PlayerAdapter(this.getActivity());
        adapter.addAll(players);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
    }


}
