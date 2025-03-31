package ru.ifmo.ts2.functions.log;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.ifmo.ts2.config.StubSaverConfig;

import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.regex.Pattern;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.compare;
import static java.lang.Double.valueOf;
import static java.lang.Math.abs;
import static java.lang.Math.log;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {
        Ln.class,
        Log2.class,
        Log3.class,
        Log5.class,
        Log10.class,
})
@Import(StubSaverConfig.class)
class LogBaseTest {

    @Value("${taylor.epsilon}")
    double epsilon;

    @Autowired
    Map<String, DoubleFunction<Double>> fnMap;

    @ParameterizedTest
    @ValueSource(strings = {
            "log2",
            "log3",
            "log5",
            "log10"
    })
    void testWithSections(String fnName) {
        final double sectionStepSize = 0.1;
        final double sectionStepCount = 10;
        final double sectionCount = 10;
        final double end = 100;
        double pointer = sectionStepSize;
        int stepCount = 0;
        double sectionSize = (end - sectionStepSize) / sectionCount;
        while (compare(pointer, end) < 0) {
            assertEqualsWithStandard(pointer, fnName);
            stepCount++;
            if (stepCount == sectionStepCount) {
                pointer += sectionSize;
                stepCount = 0;
                continue;
            }
            pointer += sectionStepSize;
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
        fnMap.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().equals("ln"))
                .map(Map.Entry::getValue)
                .forEach(fn -> assertThrows(ArithmeticException.class, () -> fn.apply(value)));
    }

    private void assertEqualsWithStandard(double value, String fnName) {
        var testFn = fnMap.get(fnName);
        int base = extractBase(fnName);
        final double expected = log(value) / log(base);
        final double actual = testFn.apply(value);
        if (valueOf(expected).isNaN()) {
            assertThat(valueOf(actual).isNaN()).isTrue();
        } else {
            double res = abs(expected - actual);
            assertThat(res).isLessThan(epsilon);
        }
    }

    private int extractBase(String fnName) {
        var pattern = Pattern.compile("\\d+");
        var matcher = pattern.matcher(fnName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return -1;
    }
}