package com.dmslob;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.AsPublisher;
import akka.stream.javadsl.JavaFlowSupport.Sink;
import akka.stream.javadsl.JavaFlowSupport.Source;
import com.dmslob.domain.TemperatureInfo;

import java.util.concurrent.Flow;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.celsiusTemperatures();
    }

    public void temperature() {
        Materializer materializer = getMaterializer();
        Flow.Publisher<TemperatureInfo> publisher = Source.fromPublisher(getTemperatures("New York"))
                .runWith(Sink.asPublisher(AsPublisher.WITH_FANOUT), materializer);
        publisher.subscribe(new TempSubscriber());

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void celsiusTemperatures() {
        Materializer materializer = getMaterializer();
        Flow.Publisher<TemperatureInfo> publisher = Source.fromPublisher(getCelsiusTemperatures("New York"))
                .runWith(Sink.asPublisher(AsPublisher.WITH_FANOUT), materializer);
        publisher.subscribe(new TempSubscriber());

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Flow.Publisher<TemperatureInfo> getTemperatures(String town) {
        return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
    }

    private Materializer getMaterializer() {
        ActorSystem system = ActorSystem.create("temp-info");
        Materializer materializer = ActorMaterializer.create(system);

        return materializer;
    }

    private Flow.Publisher<TemperatureInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe(subscriber);
            processor.onSubscribe(new TempSubscription(processor, town));
        };
    }
}
