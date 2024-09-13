package com.thermometer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThresholdTest {

    @Test
    void testIsReachedNonDirectionSensitive() {
        Threshold threshold = new Threshold(0, temp -> {}, false, false, 0.5);
        assertTrue(threshold.isReached(1, -1), "Should be reached when crossing threshold from above");
        assertTrue(threshold.isReached(-1, 1), "Should be reached when crossing threshold from below");
        assertFalse(threshold.isReached(1, 2), "Should not be reached when not crossing threshold");
    }

    @Test
    void testIsReachedDirectionSensitiveDecreasing() {
        Threshold threshold = new Threshold(0, temp -> {}, true, true, 0.5);
        assertTrue(threshold.isReached(1, -1), "Should be reached when crossing threshold from above");
        assertFalse(threshold.isReached(-1, 1), "Should not be reached when crossing threshold from below");
        assertFalse(threshold.isReached(1, 2), "Should not be reached when not crossing threshold");
    }

    @Test
    void testIsReachedDirectionSensitiveIncreasing() {
        Threshold threshold = new Threshold(0, temp -> {}, true, false, 0.5);
        assertFalse(threshold.isReached(1, -1), "Should not be reached when crossing threshold from above");
        assertTrue(threshold.isReached(-1, 1), "Should be reached when crossing threshold from below");
        assertFalse(threshold.isReached(1, 2), "Should not be reached when not crossing threshold");
    }

    @Test
    void testIsReachedWithTolerance() {
        Threshold threshold = new Threshold(0, temp -> {}, false, false, 0.5);
        assertTrue(threshold.isReached(0.4, -0.4), "Should be reached within tolerance");
        assertFalse(threshold.isReached(0.6, 0.7), "Should not be reached outside tolerance");
    }

    @Test
    void testNotifyObserver() {
        boolean[] called = {false};
        Threshold threshold = new Threshold(0, temp -> called[0] = true, false, false, 0.5);
        threshold.notifyObserver();
        assertTrue(called[0], "Observer should be called");
    }
}
