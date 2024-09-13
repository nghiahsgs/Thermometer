package com.thermometer;
import java.util.function.Consumer;

public class Threshold {
    private double value;
    private Consumer<Double> observer;
    private boolean isDirectionSensitive;
    private boolean isTriggeredOnDecreaseOnly;
    private double tolerance;

    public Threshold(double value, Consumer<Double> observer, boolean isDirectionSensitive, boolean isTriggeredOnDecreaseOnly, double tolerance) {
        this.value = value;
        this.observer = observer;
        this.isDirectionSensitive = isDirectionSensitive;
        this.isTriggeredOnDecreaseOnly = isTriggeredOnDecreaseOnly;
        this.tolerance = tolerance;
    }

    public boolean isReached(double oldTemperature, double newTemperature) {
        double oldDiff = oldTemperature - value;
        double newDiff = newTemperature - value;

        if (Math.abs(oldDiff) <= tolerance) oldDiff = 0;
        if (Math.abs(newDiff) <= tolerance) newDiff = 0;

        // System.out.printf("Threshold: %.1f°C, old_diff: %.6f, new_diff: %.6f%n", value, oldDiff, newDiff);

        if (!isDirectionSensitive) {
            return oldDiff * newDiff <= 0;
        } else {
            if (oldDiff * newDiff <= 0) {
                if (isTriggeredOnDecreaseOnly) {
                    return oldDiff > 0 && newDiff <= 0;
                } else {
                    return oldDiff < 0 && newDiff >= 0;
                }
            }
        }
        return false;
    }

    public void notifyObserver() {
        observer.accept(value);
    }
}
