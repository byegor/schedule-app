package com.egor.pulse.app.data;

/**
 * Created by Егор on 25.03.2017.
 */

public interface AwaitingData<T> {

    int getDataIdentifier();

    void dataArrived(T data);
}
