package ru.ifmo.ts2.config.trig;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.function.DoubleFunction;

import static java.util.Optional.ofNullable;

@TestConfiguration
public class SecConfig {

    private static final Map<Double, String> SEC_VALUES = Map.of(
            -Math.PI / 6, "1.1547005384",
            -Math.PI / 4, "1.4142135624",
            -Math.PI / 3, "2.0",
            0.0, "1.0",
            -Math.PI / 2, "Error",
            -Math.PI, "-1.0",
            -3 * Math.PI / 2, "Error",
            -2 * Math.PI, "1.0"
    );

    @Bean
    public DoubleFunction<Double> sec() {
        return value -> ofNullable(SEC_VALUES.get(value)).map(this::mapToDouble)
                .orElse(Double.NaN);
    }

    private Double mapToDouble(String s) {
        if (s.equals("Error")) {
            throw new ArithmeticException("Sec is undefined at this point");
        }
        return Double.parseDouble(s);
    }
}