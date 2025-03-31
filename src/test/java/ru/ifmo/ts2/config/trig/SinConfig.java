package ru.ifmo.ts2.config.trig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.function.DoubleFunction;

@TestConfiguration
public class SinConfig {

    private static final Map<Double, Double> SIN_VALUES = Map.of(
            -Math.PI / 6, -0.5,
            -Math.PI / 4, -0.7071067812,
            -Math.PI / 3, -0.8660254038,
            0.0, 0.0,
            -Math.PI / 2, -1.0,
            -Math.PI, 0.0,
            -3 * Math.PI / 2, 1.0,
            -2 * Math.PI, 0.0
    );

    @Bean
    public DoubleFunction<Double> sin() {
        return value -> SIN_VALUES.getOrDefault(value, Double.NaN);
    }
}
