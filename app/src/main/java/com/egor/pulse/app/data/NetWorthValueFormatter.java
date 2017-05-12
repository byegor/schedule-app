package com.egor.pulse.app.data;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by Iegor.Bondarenko on 12.05.2017.
 */

public class NetWorthValueFormatter implements IAxisValueFormatter {

    private DecimalFormat mFormat = new DecimalFormat("###,###,###,##0");

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value < 0 ? -value : value);
    }
}
