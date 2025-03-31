package ru.ifmo.ts2.functions.log;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.ifmo.ts2.report.CsvResultSaver;

import java.util.function.DoubleFunction;

import static java.lang.Double.compare;
import static java.lang.Double.isNaN;
import static java.lang.Math.abs;

@RequiredArgsConstructor
public class Ln implements DoubleFunction<Double> {

    @Value("${taylor.epsilon}")
    private double epsilon;

    private final CsvResultSaver saver;

    @Override
    public Double apply(double value) {
        validateInput(value);
        value = (value - 1) / (value + 1);
        double term = value;
        double sum = 0;
        int n = 1;
        while (abs(term) > epsilon) {
            sum += term / n;
            term *= value * value;
            n += 2;
        }
        double res = 2 * sum;
        saver.saveResult(String.format("ln(%.3f)", value), res);
        return res;
    }

    private void validateInput(double value) {
        if (compare(abs(value), epsilon) <= 0
                || compare(0, value) >= epsilon) {
            throw new ArithmeticException("ln(x) не определён для x <= 0");
        }
        if (isNaN(value)) {
            throw new ArithmeticException("Input value is NaN");
        }
    }
}
