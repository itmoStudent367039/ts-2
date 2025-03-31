package ru.ifmo.ts2.config.trig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.function.DoubleFunction;

import static java.util.Optional.ofNullable;

@TestConfiguration
public class CotConfig {

    private static final Map<Double, String> Cot_VALUES = Map.of(
            -Math.PI / 6, "-1.7320508076",
            -Math.PI / 4, "-1.0",
            -Math.PI / 3, "-0.5773502692",
            -Math.PI / 2, "0.0",
            -3 * Math.PI / 2, "0.0",
            0., "Error",
            -Math.PI, "Error",
            -2 * Math.PI, "Error"
    );

    @Bean
    public DoubleFunction<Double> cot() {
        return value -> ofNullable(Cot_VALUES.get(value)).map(this::mapToDouble)
                .orElse(Double.NaN);
    }

    private Double mapToDouble(String s) {
        if (s.equals("Error")) {
            throw new ArithmeticException("Error");
        }
        return Double.parseDouble(s);
    }
}
