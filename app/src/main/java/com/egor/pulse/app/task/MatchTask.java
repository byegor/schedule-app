package com.egor.pulse.app.task;

import android.os.AsyncTask;
import android.util.Log;

import com.eb.schedule.shared.bean.Match;
import com.egor.pulse.app.data.MatchDataStorage;
import com.egor.pulse.app.match.MatchWrapperFragment;

/**
 * Created by Егор on 12.04.2017.
 */

public class MatchTask extends AsyncTask<Integer, Void, Match> {

    MatchWrapperFragment fragment;

    public MatchTask(MatchWrapperFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected Match doInBackground(Integer... ids) {
        for (int i = 0; i < 50; i++) {
            Match data = MatchDataStorage.getData(ids[0], ids[1]);
            if(data != null){
                return data;
            }else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        }
        Log.e("API", "Coudln't retrieve data for game Id: " + ids[0] + " and gameNumber: " + ids[1]);
        return null;
    }

    @Override
    protected void onPostExecute(Match s) {
        super.onPostExecute(s);
        if(s != null){
            fragment.refresh(s);
        }else{
            fragment.failedToGetData();
        }

    }
}
