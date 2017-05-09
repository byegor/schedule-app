package com.egor.pulse.app.match;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eb.schedule.shared.bean.Item;
import com.eb.schedule.shared.bean.Player;
import com.eb.schedule.shared.bean.TeamBean;
import com.egor.pulse.app.R;
import com.egor.pulse.app.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Egor on 02.07.2016.
 */
// todo improve layout for players
public class MatchPlayerFragment extends Fragment {

    private TeamBean team;
    private Boolean radiant;

    public static MatchPlayerFragment newInstance(TeamBean teamBean, boolean radiant) {
        MatchPlayerFragment fragment = new MatchPlayerFragment();
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
        final LinearLayout players = (LinearLayout) view.findViewById(R.id.players);
        for (Player player : team.getPlayers()) {
            players.addView(renderPlayer(player));
        }

    }

    private View renderPlayer(Player player) {
        View rowView = getActivity().getLayoutInflater().inflate(R.layout.player, null);
        ImageView heroImage = (ImageView) rowView.findViewById(R.id.hero_log);
        int heroId = player.getHero().getId();
        if (heroId != 0) {
            ImageUtils.loadHeroImage(getContext(), player.getHero(), heroImage);
        }

        TextView playerName = (TextView) rowView.findViewById(R.id.player_name);
        playerName.setText(player.getName());

        if(player.getLevel() != 0){
            TextView level = (TextView) rowView.findViewById(R.id.level_status);
            level.setText("Lvl " + player.getLevel() + ": " + player.getHero().getName());

            TextView kda = (TextView) rowView.findViewById(R.id.kda);
            kda.setText("K/D/A: " + player.getKills() + "/" + player.getDeaths() + "/" + player.getAssists());

            List<Item> items = player.getItems();
            for (int i = 0; i < items.size(); i++) {
                ImageView itemImage = (ImageView) rowView.findViewById(getContext().getResources().getIdentifier("item_" + (i + 1), "id", getContext().getPackageName()));
                Item item = items.get(i);
                if (item.getName().contains("recipe")) {
                    Picasso.with(getContext()).load(R.drawable.i_recipe).resize(30, 30).centerCrop().into(itemImage);
                } else {
                    Picasso.with(getContext()).load(ImageUtils.getItemUrl(item.getName())).resize(30, 30).centerCrop().into(itemImage);
                }
            }
        }
        return rowView;
    }


}
