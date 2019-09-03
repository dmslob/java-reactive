package com.dmslob;

import com.dmslob.domain.TemperatureInfo;
import io.reactivex.Observable;

import static com.dmslob.TempObservable.getCelsiusTemperatures;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        //main.observeTemp();
        main.observeTempCelsius();
    }

    private void observeTemp() {
        Observable<TemperatureInfo> observable = TempObservable.getTemperature("New York");
        observable.subscribe(new TempObserver());
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void observeTempCelsius() {
        Observable<TemperatureInfo> observable = getCelsiusTemperatures("New York", "Chicago", "San Francisco");
        observable.subscribe(new TempObserver());
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
