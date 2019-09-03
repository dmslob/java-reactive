package com.dmslob;

import com.dmslob.domain.TemperatureInfo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TempObserver implements Observer<TemperatureInfo> {

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Got problem: " + throwable.getMessage());
    }

    @Override
    public void onSubscribe(Disposable disposable) {
    }

    @Override
    public void onNext(TemperatureInfo tempInfo) {
        System.out.println(tempInfo);
    }
}
