package com.egor.schedule.app.adapter;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.eb.schedule.shared.bean.LeagueBean;
import com.eb.schedule.shared.bean.Match;
import com.eb.schedule.shared.bean.TeamBean;
import com.egor.schedule.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.egor.schedule.app.utils.ImageUtils.BASE_URL;

/**
 * Created by Egor on 02.07.2016.
 */
public class MatchTeamInfo extends Fragment {

    private Match match;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_team_info, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView leagueName = (TextView) view.findViewById(R.id.league_name);
        LeagueBean league = match.getLeague();
        if (league != null) {
            leagueName.setText(league.getName());
        }

        TextView seriesType = (TextView) view.findViewById(R.id.series_type);
        String series = match.getSeriesType();
        if (series != null) {
            seriesType.setText(series);
        }


        TeamBean radiantTeam = match.getRadiantTeam();
        if (radiantTeam != null) {
            View radiantLogo = view.findViewById(R.id.radiant_logo);
            Picasso.with(getActivity()).load(BASE_URL + radiantTeam.getLogo()).into((ImageView) radiantLogo);
            TextView radiantName = (TextView) view.findViewById(R.id.radiant_team_name);
            radiantName.setText(radiantTeam.getName());

            TextView score = (TextView) view.findViewById(R.id.radiant_score);
            score.setText(match.getRadiantScore());
        }

        TeamBean direTeam = match.getDireTeam();
        if (direTeam != null) {
            View direLogo = view.findViewById(R.id.dire_logo);
            Picasso.with(getActivity()).load(BASE_URL + direTeam.getLogo()).into((ImageView) direLogo);
            TextView direName = (TextView) view.findViewById(R.id.dire_team_name);
            direName.setText(direTeam.getName());

            TextView score = (TextView) view.findViewById(R.id.dire_team_name);
            score.setText(match.getDireScore());
        }

        TextView duration = (TextView) view.findViewById(R.id.match_duration);
        duration.setText(match.getDuration() + "");

        TextView netWorthAdv = (TextView) view.findViewById(R.id.net_worth_adv);
        List<Double> networthList = match.getNetworth();
        if (networthList != null && !networthList.isEmpty()) {
            //todo display number
            Double networth = networthList.get(networthList.size() - 1);
            Resources res = getResources();
            if (networth > 0) {
                netWorthAdv.setText(String.format(res.getString(R.string.net_worth_adv), match.getRadiantTeam().getName()));
            } else {
                netWorthAdv.setText(String.format(res.getString(R.string.net_worth_adv), match.getDireTeam().getName()));
            }
        }

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        match = (Match) args.get("match");
    }
}
