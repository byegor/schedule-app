package com.egor.schedule.app.adapter.schedule;

/**
 * Created by Егор on 12.10.2016.
 */

public class ListHeader implements ListAddapterItem<String> {

    String date;

    public ListHeader(String date) {
        this.date = date;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public String getBody() {
        return date;
    }
}
