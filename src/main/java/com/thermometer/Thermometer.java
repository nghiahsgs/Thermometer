package com.thermometer;
import java.util.ArrayList;
import java.util.List;

public class Thermometer {
    private double temperature;
    private List<Threshold> thresholds;

    // Constants for temperature conversion
    private static final double FAHRENHEIT_SCALE = 9.0 / 5.0;
    private static final double FAHRENHEIT_OFFSET = 32.0;

    public Thermometer() {
        this.temperature = 0;
        this.thresholds = new ArrayList<>();
    }

    public void setTemperature(double celsius) {
        double oldTemperature = this.temperature;
        this.temperature = celsius;
        checkThresholds(oldTemperature, this.temperature);
    }

    public double getTemperatureCelsius() {
        return temperature;
    }

    public double getTemperatureFahrenheit() {
        return (temperature * FAHRENHEIT_SCALE) + FAHRENHEIT_OFFSET;
    }

    public void addThreshold(Threshold threshold) {
        thresholds.add(threshold);
    }

    private void checkThresholds(double oldTemperature, double newTemperature) {
        for (Threshold threshold : thresholds) {
            if (threshold.isReached(oldTemperature, newTemperature)) {
                threshold.notifyObserver();
            }
        }
    }
}
