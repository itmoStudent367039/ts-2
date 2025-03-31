package ru.ifmo.ts2.functions.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.ifmo.ts2.config.StubSaverConfig;

import static java.lang.Double.*;
import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Math.abs;
import static java.lang.Math.log;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Ln.class)
@Import(StubSaverConfig.class)
class LnTest {

    private static final double SECTION_STEP_SIZE = 0.1;
    private static final double SECTION_STEP_COUNT = 10;
    private static final double SECTION_COUNT = 10;

    @Value("${taylor.epsilon}")
    double epsilon;

    @Autowired
    Ln ln;

    @Test
    void testWithSections() {
        double pointer = SECTION_STEP_SIZE;
        int stepCount = 0;
        final double end = 100;
        double sectionSize = (end - SECTION_STEP_SIZE) / SECTION_COUNT;
        while (compare(pointer, end) < 0) {
            assertEqualsWithStandard(pointer);
            stepCount++;
            if (stepCount == SECTION_STEP_COUNT) {
                pointer += sectionSize;
                stepCount = 0;
                continue;
            }
            pointer += SECTION_STEP_SIZE;
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            MIN_VALUE,
            0,
            -1,
            NaN,
            -MAX_VALUE,
            NEGATIVE_INFINITY
    })
    void testCriticalShouldThrowException(double value) {
        assertThrows(ArithmeticException.class, () -> ln.apply(value));
    }

    private void assertEqualsWithStandard(double value) {
        final double expected = log(value);
        final double actual = ln.apply(value);
        if (valueOf(expected).isNaN()) {
            assertThat(valueOf(actual).isNaN()).isTrue();
        } else {
            double res = abs(expected - actual);
            assertThat(res).isLessThan(epsilon);
        }
    }
}