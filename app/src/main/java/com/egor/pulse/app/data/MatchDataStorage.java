package com.egor.pulse.app.data;

import com.eb.schedule.shared.bean.Match;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Егор on 25.03.2017.
 */

public class MatchDataStorage {

    private static volatile ConcurrentMap<Integer, List<Match>> storedData = new ConcurrentHashMap<Integer, List<Match>>();

    public static void setData(int id, List<Match> data) {
        storedData.put(id, data);
    }

    public static Match getData(int gameId, int gameNumber){
        List<Match> data = storedData.get(gameId);
        if(data != null && !data.isEmpty()){
            return data.get(gameNumber);
        }else{
            return null;
        }
    }

    public static void removeData(int gameId){
        storedData.remove(gameId);
    }
}
