package com.dmslob;

import com.dmslob.domain.TemperatureInfo;

import java.util.concurrent.Flow.Publisher;

public class FlowDemo {

    public static void main(String[] args) {
        FlowDemo demo = new FlowDemo();
        //demo.flow();
        demo.flowWithProcessor();
    }

    private void flowWithProcessor() {
        getCelsiusTemperatures("New York").subscribe(new TempSubscriber());
    }

    private Publisher<TemperatureInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }

    private void flow() {
        getTemperatures("New York").subscribe(new TempSubscriber());
    }

    private Publisher<TemperatureInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }
}
