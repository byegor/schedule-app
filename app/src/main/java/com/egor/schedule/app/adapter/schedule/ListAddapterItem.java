package com.egor.schedule.app.adapter.schedule;

/**
 * Created by Егор on 12.10.2016.
 */

public interface ListAddapterItem<T> {
    boolean isHeader();
    T getBody();
}
