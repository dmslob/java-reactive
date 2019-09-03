package com.dmslob.domain;

import java.util.Random;

/**
 * A remote thermometer (constantly reporting randomly
 * chosen temperatures between 0 and 99 degrees Fahrenheit, which is appropriate for U.S. cities most of the time)
 */
public class TemperatureInfo {
    public static final Random random = new Random();

    private final String town;
    private final int temperature;

    public TemperatureInfo(String town, int temperature) {
        this.town = town;
        this.temperature = temperature;
    }

    public static TemperatureInfo fetch(String town) {
        if (random.nextInt(10) == 0) {
            throw new RuntimeException("Error!");
        }
        return new TemperatureInfo(town, random.nextInt(100));
    }

    @Override
    public String toString() {
        return town + " : " + temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getTown() {
        return town;
    }
}
