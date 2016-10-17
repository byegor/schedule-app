package com.egor.schedule.app.adapter.schedule;

import com.eb.schedule.shared.bean.GameBean;

/**
 * Created by Егор on 12.10.2016.
 */

public class ListItem implements ListAddapterItem<GameBean> {

    GameBean gameBean;

    public ListItem(GameBean gameBean) {
        this.gameBean = gameBean;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public GameBean getBody() {
        return gameBean;
    }
}
