package com.egor.pulse.app.match;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egor.pulse.app.R;
import com.egor.pulse.app.data.NetWorthValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

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
        LineChart graph = (LineChart) view.findViewById(R.id.chart);

        ArrayList<Integer> networth = (ArrayList<Integer>) this.getArguments().getSerializable("net");

        String[] split = this.getArguments().getString("duration").split(":");
        int seconds = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
        int timeStep = seconds / networth.size();
        List<Entry> entries = new ArrayList<Entry>();
        double xAxis = 0;
        for (Integer nw : networth) {
            entries.add(new Entry((float) xAxis, nw));
            xAxis += timeStep;
        }

        LineDataSet dataSet = new LineDataSet(entries, ""); // add entries to dataset
        dataSet.setColor(Color.YELLOW);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
        graph.setData(lineData);
        graph.getAxisLeft().setEnabled(false);
        graph.getAxisRight().setValueFormatter(new NetWorthValueFormatter());
        graph.getAxisRight().setTextColor(Color.WHITE);

        graph.setTouchEnabled(false);
        graph.getLegend().setEnabled(false);
        graph.setDescription(null);

        graph.invalidate();
    }
}
