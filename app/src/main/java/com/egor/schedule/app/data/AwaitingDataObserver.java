package com.egor.schedule.app.data;

import com.eb.schedule.shared.bean.Match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Егор on 25.03.2017.
 */

public class AwaitingDataObserver {

    static volatile Map<Integer, List<AwaitingData<Match>>> listeners = new HashMap<Integer, List<AwaitingData<Match>>>();


    public static void addListener(int id, AwaitingData<Match> listenr) {
        List<AwaitingData<Match>> awaitingDatas = listeners.get(id);
        if (awaitingDatas == null) {
            awaitingDatas = new ArrayList<AwaitingData<Match>>();
            listeners.put(id, awaitingDatas);
        }
        awaitingDatas.add(listenr);
    }

    public static void notify(int id, Map<Integer, Match> data) {
        List<AwaitingData<Match>> awaitingDatas = listeners.get(id);
        if (awaitingDatas != null) {
            for (AwaitingData<Match> listener : awaitingDatas) {
                Match match = data.remove(listener.getDataIdentifier());
                if (match != null) {
                    listener.dataArrived(match);
                }
            }
        }
    }
}
