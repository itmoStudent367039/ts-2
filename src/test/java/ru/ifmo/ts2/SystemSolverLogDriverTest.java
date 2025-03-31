package ru.ifmo.ts2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.ifmo.ts2.config.StubSaverConfig;
import ru.ifmo.ts2.functions.log.Ln;
import ru.ifmo.ts2.functions.log.Log10;
import ru.ifmo.ts2.functions.log.Log2;
import ru.ifmo.ts2.functions.log.Log3;
import ru.ifmo.ts2.functions.log.Log5;

import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        Ln.class,
        Log2.class,
        Log3.class,
        Log5.class,
        Log10.class,
})
@Import(StubSaverConfig.class)
class SystemSolverLogDriverTest {

    @Autowired
    Map<String, DoubleFunction<Double>> fnMap;

    public static Stream<Arguments> getDataSource() {
        return Stream.of(
                Arguments.of(0.1, new double[]{
                        -3.7526094697911896,
                        19.29684237503113,
                        -5.7526094697911905,
                        9.584626831397486
                }),
                Arguments.of(0.5, new double[]{
                        -1.3400572848137615,
                        0.15846123041679525,
                        -4.7526086232219384,
                        0.8685475426917328
                }),
                Arguments.of(10, new double[]{
                        5.7526094697911905,
                        19.296842375031165,
                        -2.891255573475042,
                        9.584626831397495
                })
        );
    }

    @ParameterizedTest
    @MethodSource("getDataSource")
    void test(double value, double[] expected) {
        var first = calculateFirst(value);
        var second = calculateSecond(value);
        var third = calculateThird(value);
        var fourth = calculateFourth(value);
        var res = (first + second + third) / fourth;
        assertThat(res).isEqualTo(answers().get(value));
        assertThat(first).isEqualTo(expected[0]);
        assertThat(second).isEqualTo(expected[1]);
        assertThat(third).isEqualTo(expected[2]);
        assertThat(fourth).isEqualTo(expected[3]);
    }

    private Map<Double, Double> answers() {
        return Map.of(
                0.1, 1.0215967306492497,
                0.5, -6.83233143372681,
                10., 2.311847572277002
        );
    }

    private double calculateFirst(double value) {
        double log2 = fnMap.get("log2").apply(value);
        double log5 = fnMap.get("log5").apply(value);
        double log10 = fnMap.get("log10").apply(value);
        return (log5 + log2) + (log10 * log10);
    }

    private double calculateSecond(double value) {
        double log3 = fnMap.get("log3").apply(value);
        return log3 * Math.pow(log3, 3);
    }

    private double calculateThird(double value) {
        double log2 = fnMap.get("log2").apply(value);
        double log5 = fnMap.get("log5").apply(value);
        double log10 = fnMap.get("log10").apply(value);
        return log5 - ((log10 + log2) / log10);
    }

    private double calculateFourth(double value) {
        double log3 = fnMap.get("log3").apply(value);
        double log10 = fnMap.get("log10").apply(value);
        return Math.pow((log10 + log3), 2);
    }
}