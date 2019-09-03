package com.dmslob;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.dmslob.domain.TemperatureInfo;
import io.reactivex.Observable;

public class TempObservable {

    public static Observable<TemperatureInfo> getTemperature(String town) {
        return Observable.create(emitter -> Observable.interval(1, TimeUnit.SECONDS).subscribe(i -> {
            if (!emitter.isDisposed()) {
                if (i >= 5) {
                    emitter.onComplete();
                } else {
                    try {
                        emitter.onNext(TemperatureInfo.fetch(town));
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }
            }
        }));
    }

    public static Observable<TemperatureInfo> getCelsiusTemperature(String town) {
        return getTemperature(town)
                .map(temp -> new TemperatureInfo(temp.getTown(), TempUtil.getCelsius(temp.getTemperature())));
    }

    public static Observable<TemperatureInfo> getNegativeTemperature(String town) {
        return getCelsiusTemperature(town)
                .filter(temp -> temp.getTemperature() < 0);
    }

    public static Observable<TemperatureInfo> getCelsiusTemperatures(String... towns) {
        return Observable.merge(Arrays.stream(towns)
                .map(TempObservable::getCelsiusTemperature)
                .collect(toList()));
    }
}
