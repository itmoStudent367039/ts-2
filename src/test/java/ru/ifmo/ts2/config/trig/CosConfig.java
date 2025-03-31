package ru.ifmo.ts2.config.trig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.function.DoubleFunction;

@TestConfiguration
public class CosConfig {

    private static final Map<Double, Double> COS_VALUES = Map.of(
            -Math.PI / 6, 0.8660254038,
            -Math.PI / 4, 0.7071067812,
            -Math.PI / 3, 0.5,
            0.0, 1.0,
            -Math.PI / 2, 0.0,
            -Math.PI, -1.0,
            -3 * Math.PI / 2, 0.0,
            -2 * Math.PI, 1.0
    );

    @Bean
    public DoubleFunction<Double> cos() {
        return value -> COS_VALUES.getOrDefault(value, Double.NaN);
    }
}
