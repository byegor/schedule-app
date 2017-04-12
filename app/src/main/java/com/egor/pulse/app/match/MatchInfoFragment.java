package com.egor.pulse.app.match;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eb.schedule.shared.bean.HeroBean;
import com.eb.schedule.shared.bean.Match;
import com.eb.schedule.shared.bean.TeamBean;
import com.egor.pulse.app.R;
import com.egor.pulse.app.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Egor on 02.07.2016.
 */
//todo check live games
public class MatchInfoFragment extends Fragment {

    private Match match;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.match = (Match) getArguments().getSerializable("match");
    }

    public static MatchInfoFragment newInstance(Match match) {
        MatchInfoFragment fragment = new MatchInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("match", match);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_team_info, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (match != null) {
            TeamBean radiantTeam = match.getRadiantTeam();
            if (radiantTeam != null) {
                View radiantLogo = view.findViewById(R.id.radiant_logo);
                Picasso.with(getParentFragment().getActivity()).load(ImageUtils.getTeamUrl(radiantTeam.getLogo())).into((ImageView) radiantLogo);
                TextView radiantName = (TextView) view.findViewById(R.id.radiant_team_name);
                radiantName.setText(radiantTeam.getName());

                TextView score = (TextView) view.findViewById(R.id.match_score);
                score.setText(match.getMatchScore());
            }

            TeamBean direTeam = match.getDireTeam();
            if (direTeam != null) {
                View direLogo = view.findViewById(R.id.dire_logo);
                Picasso.with(getParentFragment().getActivity()).load(ImageUtils.getTeamUrl(direTeam.getLogo())).into((ImageView) direLogo);
                TextView direName = (TextView) view.findViewById(R.id.dire_team_name);
                direName.setText(direTeam.getName());
            }

            TextView duration = (TextView) view.findViewById(R.id.match_duration);
            if (match.getDuration() != null) {
                duration.setText(match.getDuration());
            }


            TextView winner = (TextView) view.findViewById(R.id.team_victory);
            List<Integer> networthList = match.getNetworth();
            if (match.getMatchStatus() != 0) {
                winner.setText("Winner: " + (match.getMatchStatus() == 1 ? match.getRadiantTeam().getName() : match.getDireTeam().getName()));
            } else {
                if (networthList != null && !networthList.isEmpty() && !match.getDuration().equals("0:00")) {
                    Integer networth = networthList.get(0);
                    if (networth > 0) {
                        winner.setText("Net Worth Advantage: " + match.getRadiantTeam().getName() + " - " + networth);
                    } else {
                        winner.setText("Net Worth Advantage: " + match.getDireTeam().getName() + " - " + (-networth));
                    }
                } else {
                    winner.setText("Pick/Ban stage");
                }
            }


//picks and bans
            initPicks(view, match.getRadianPicks(), "radiant_pick_");
            initPicks(view, match.getRadianBans(), "radiant_ban_");
            initPicks(view, match.getDirePicks(), "dire_pick_");
            initPicks(view, match.getDireBans(), "dire_ban_");
        }
    }

    private void initPicks(View view, List<HeroBean> heroes, String prefix) {
        if (heroes != null) {
            for (int i = 0; i < heroes.size(); i++) {
                ImageView pick = (ImageView) view.findViewById(getResources().getIdentifier(prefix + (i + 1), "id", getParentFragment().getActivity().getPackageName()));
                HeroBean hero = heroes.get(i);
                ImageUtils.loadHeroImage(getContext(), hero, pick);
                pick.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        match = (Match) args.get("match");
    }
}
