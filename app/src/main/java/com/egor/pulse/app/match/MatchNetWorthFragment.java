package com.egor.pulse.app.match;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egor.pulse.app.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 02.07.2016.
 */
public class MatchNetWorthFragment extends Fragment {

    public static MatchNetWorthFragment newInstance(List<Integer> networth, String duration) {
        MatchNetWorthFragment fragment = new MatchNetWorthFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("net", new ArrayList<Integer>(networth));
        bundle.putString("duration", duration);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_networth, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return "";
//                    return getDuration((int) value);
                } else {
                    return super.formatLabel(value < 0 ? -value : value, isValueX);
                }
            }
        });
        ArrayList<Integer> networth = (ArrayList<Integer>) this.getArguments().getSerializable("net");

        String[] split = this.getArguments().getString("duration").split(":");
        int seconds = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
        int timeStep = seconds / networth.size();
        List<DataPoint> data = new ArrayList<DataPoint>(networth.size());
        double xAxis = 0;
        for (Integer nw : networth) {
            data.add(new DataPoint(xAxis, nw));
            xAxis += timeStep;
        }
        graph.getViewport().setScalable(false);
        graph.getViewport().setScrollable(false);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(xAxis);
        graph.getViewport().setXAxisBoundsManual(true);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(data.toArray(new DataPoint[data.size()]));
        series.setColor(Color.YELLOW);
        graph.addSeries(series);

    }
}
