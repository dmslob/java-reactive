package com.dmslob;

import com.dmslob.domain.TemperatureInfo;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class TempProcessor implements Processor<TemperatureInfo, TemperatureInfo> {
    private Subscriber<? super TemperatureInfo> subscriber;

    @Override
    public void subscribe(Subscriber<? super TemperatureInfo> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void onNext(TemperatureInfo temp) {
        subscriber.onNext(
                new TemperatureInfo(
                        temp.getTown(),
                        TempUtil.getCelsius(temp.getTemperature())
                ));
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onError(Throwable throwable) {
        subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        subscriber.onComplete();
    }
}
