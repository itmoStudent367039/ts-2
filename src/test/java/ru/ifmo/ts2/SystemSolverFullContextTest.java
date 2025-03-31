package ru.ifmo.ts2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.ifmo.ts2.config.AppConfig;

import java.util.stream.Stream;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(AppConfig.class)
class SystemSolverFullContextTest {

    static final double SECTION_COUNT = 10;

    final double epsilon = 0.0001;

    @Autowired
    SystemSolver solver;

    public static Stream<Arguments> getTrigValues() {
        return Stream.of(
                Arguments.of(-PI / 6, -0.144337),
                Arguments.of(-PI / 3, -1.29903),
                Arguments.of(-PI / 4, -0.5)
        );
    }

    public static Stream<Arguments> getLogValues() {
        return Stream.of(
                Arguments.of(0.1, 1.02159),
                Arguments.of(0.5, -6.83231),
                Arguments.of(10, 2.31184)
        );
    }

    @ParameterizedTest
    @MethodSource("getLogValues")
    void logBase(double value, double expected) {
        var result = solver.calculate(value);
        boolean isEquals = Double.compare(Math.abs(result - expected), epsilon) <= 0;
        assertTrue(isEquals);
    }

    @Test
    void logCriticalShouldThrowException() {
        final double logCritical = 1;
        assertThrows(ArithmeticException.class, () -> solver.calculate(logCritical));
    }

    @ParameterizedTest
    @MethodSource("getTrigValues")
    void testTrigBase(double value, double expected) {
        for (int i = 0; i < SECTION_COUNT; i++) {
            double valueWithOffset = value - 2 * PI * i;
            var result = solver.calculate(valueWithOffset);
            boolean isEquals = Double.compare(Math.abs(result - expected), epsilon) <= 0;
            assertTrue(isEquals);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            0,
            -PI / 2,
            -PI,
            -3 * PI / 2,
            -2 * PI
    })
    void trigCriticalShouldThrowException(double value) {
        for (int i = 0; i < SECTION_COUNT; i++) {
            double valueWithOffset = value - 2 * PI * i;
            assertThrows(ArithmeticException.class, () -> solver.calculate(valueWithOffset));
        }
    }
}