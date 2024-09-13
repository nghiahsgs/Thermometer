package com.thermometer;
public class Main {
    public static void main(String[] args) {
        Thermometer thermometer = new Thermometer();

        // Add threshold for freezing point (0°C) with 0.5°C tolerance
        Threshold freezingThreshold = new Threshold(0, Main::alert, true, true, 0.5);
        thermometer.addThreshold(freezingThreshold);

        // Add threshold for boiling point (100°C) regardless of direction
        Threshold boilingThreshold = new Threshold(100, Main::alert, false, false, 1);
        thermometer.addThreshold(boilingThreshold);

        // Simulate temperature changes
        double[] temperatures = {25, 10, 5, 1, 0.2, -0.3, 0, 0.5, 1, 50, 105, 110, 97};
        for (double temp : temperatures) {
            System.out.printf("%nCurrent temperature: %.1f°C (%.2f°F)%n", temp, thermometer.getTemperatureFahrenheit());
            thermometer.setTemperature(temp);
        }
    }

    private static void alert(double temp) {
        System.out.printf("=>>> Alert: Temperature has reached the threshold of %.1f°C!%n", temp);
    }
}
