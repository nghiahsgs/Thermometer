package com.thermometer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ThermometerTest {
    private Thermometer thermometer;

    @Mock
    private Consumer<Double> mockObserver;

    @BeforeEach
    void setUp() {
        thermometer = new Thermometer();
    }

    @Test
    void testInitialTemperature() {
        assertEquals(0, thermometer.getTemperatureCelsius(), "Initial temperature should be 0°C");
    }

    @Test
    void testSetTemperature() {
        thermometer.setTemperature(25);
        assertEquals(25, thermometer.getTemperatureCelsius(), "Temperature should be set to 25°C");
    }

    @Test
    void testGetTemperatureFahrenheit() {
        thermometer.setTemperature(0);
        assertEquals(32, thermometer.getTemperatureFahrenheit(), "0°C should be 32°F");

        thermometer.setTemperature(100);
        assertEquals(212, thermometer.getTemperatureFahrenheit(), "100°C should be 212°F");
    }

    @Test
    void testFreezingThreshold() {
        Threshold freezingThreshold = new Threshold(0, mockObserver, true, true, 0.5);
        thermometer.addThreshold(freezingThreshold);

        thermometer.setTemperature(5);
        verifyNoInteractions(mockObserver);

        thermometer.setTemperature(-0.3);
        verify(mockObserver).accept(0.0);

        reset(mockObserver);
        thermometer.setTemperature(-1);
        verifyNoInteractions(mockObserver);

        thermometer.setTemperature(0.2);
        verifyNoInteractions(mockObserver);
    }

    @Test
    void testBoilingThreshold() {
        Threshold boilingThreshold = new Threshold(100, mockObserver, false, false, 1);
        thermometer.addThreshold(boilingThreshold);

        thermometer.setTemperature(101);
        verify(mockObserver).accept(100.0);
    }

    @Test
    void testMultipleThresholds() {
        Threshold freezingThreshold = new Threshold(0, mockObserver, true, true, 0.5);
        Threshold boilingThreshold = new Threshold(100, mockObserver, false, false, 1);
        thermometer.addThreshold(freezingThreshold);
        thermometer.addThreshold(boilingThreshold);

        thermometer.setTemperature(50);
        verifyNoInteractions(mockObserver);

        thermometer.setTemperature(-0.3);
        verify(mockObserver).accept(0.0);

        reset(mockObserver);
        thermometer.setTemperature(100.5);
        verify(mockObserver).accept(100.0);
    }
}
