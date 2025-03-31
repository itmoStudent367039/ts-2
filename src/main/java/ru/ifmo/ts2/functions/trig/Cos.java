package ru.ifmo.ts2.functions.trig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.ifmo.ts2.report.CsvResultSaver;

import java.util.function.DoubleFunction;

@RequiredArgsConstructor
public class Cos implements DoubleFunction<Double> {

    @Value("${taylor.epsilon}")
    private double epsilon;

    private final CsvResultSaver saver;

    @Override
    public Double apply(double value) {
        value %= (2 * Math.PI);
        double term = 1.0;
        double sum = 1.0;
        int n = 1;
        while (Math.abs(term) > epsilon) {
            term *= -value * value / ((2 * n - 1) * (2 * n));
            sum += term;
            n++;
        }
        var res = changeIfCritical(sum);
        saver.saveResult(String.format("cos(%.3f)", value), res);
        return res;
    }

    private Double changeIfCritical(double sum) {
        if (isEqualsWithEpsilon(sum, 1.)) {
            return 1.;
        } else if (isEqualsWithEpsilon(sum, 0.)) {
            return 0.;
        } else if (isEqualsWithEpsilon(sum, -1.)) {
            return -1.;
        }
        return sum;
    }

    private boolean isEqualsWithEpsilon(double sum, double expected) {
        return Double.compare(Math.abs(sum - expected), epsilon) < 0;
    }
}
