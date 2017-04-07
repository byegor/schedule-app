package com.egor.schedule.app.adapter.schedule.item;

import com.eb.schedule.shared.bean.GameBean;
import com.egor.schedule.app.adapter.schedule.item.ScheduleItem;

/**
 * Created by Егор on 12.10.2016.
 */

public class ScheduleItemGame implements ScheduleItem<GameBean> {

    private GameBean gameBean;

    public ScheduleItemGame(GameBean gameBean) {
        this.gameBean = gameBean;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public GameBean getItem() {
        return gameBean;
    }
}
