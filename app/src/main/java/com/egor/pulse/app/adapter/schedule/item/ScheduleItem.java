package com.egor.pulse.app.adapter.schedule.item;

/**
 * Created by Егор on 12.10.2016.
 */

public interface ScheduleItem<T> {
    boolean isHeader();
    T getItem();
}
