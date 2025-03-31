package ru.ifmo.ts2.config.trig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.function.DoubleFunction;

import static java.util.Optional.ofNullable;

@TestConfiguration
public class TanConfig {

    private static final Map<Double, String> TAN_VALUES = Map.of(
            -Math.PI / 6, "-0.5773502692",
            -Math.PI / 4, "-1.0",
            -Math.PI / 3, "-1.7320508076",
            0.0, "0.0",
            -Math.PI / 2, "Error",
            -Math.PI, "0.0",
            -3 * Math.PI / 2, "Error",
            -2 * Math.PI, "0.0"
    );

    @Bean
    public DoubleFunction<Double> tan() {
        return value -> ofNullable(TAN_VALUES.get(value)).map(this::mapToDouble)
                .orElse(Double.NaN);
    }

    private Double mapToDouble(String s) {
        if (s.equals("Error")) {
            throw new ArithmeticException("Tan is undefined at this point");
        }
        return Double.parseDouble(s);
    }
}
