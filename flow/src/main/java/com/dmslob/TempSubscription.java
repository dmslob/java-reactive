package com.dmslob;

import com.dmslob.domain.TemperatureInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;


/**
 * Subscription for the temperatures of a given town that sends a temperature report whenever
 * this report is requested by its Subscriber
 */
public class TempSubscription implements Subscription {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final Subscriber<? super TemperatureInfo> subscriber;
    private final String town;

    public TempSubscription(Subscriber<? super TemperatureInfo> subscriber, String town) {
        this.subscriber = subscriber;
        this.town = town;
    }

    @Override
    public void request(long n) {
        executor.submit(() -> {
            for (long i = 0L; i < n; i++) {
                try {
                    TemperatureInfo fetchedTempForTown = TemperatureInfo.fetch(town);
                    subscriber.onNext(fetchedTempForTown);
                } catch (Exception e) {
                    subscriber.onError(e);
                    break;
                }
            }
        });
    }

    @Override
    public void cancel() {
        subscriber.onComplete();
    }
}
