package com.egor.schedule.app.adapter.schedule.item;

/**
 * Created by Егор on 12.10.2016.
 */

public class ScheduleItemHeader implements ScheduleItem<String> {

    private String date;

    public ScheduleItemHeader(String date) {
        this.date = date;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public String getItem() {
        return date;
    }
}
