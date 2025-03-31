package ru.ifmo.ts2.functions.trig;

import lombok.RequiredArgsConstructor;
import ru.ifmo.ts2.report.CsvResultSaver;

import java.util.function.DoubleFunction;

@RequiredArgsConstructor
public class Tan implements DoubleFunction<Double> {

    private final Cos cos;
    private final Sin sin;
    private final CsvResultSaver saver;

    @Override
    public Double apply(double value) {
        var cosValue = cos.apply(value);
        if (Double.compare(cosValue, 0.0) == 0) {
            throw new ArithmeticException("Value out of range: " + value);
        }
        double res = sin.apply(value) / cosValue;
        saver.saveResult(String.format("tan(%.3f)", value), res);
        return res;
    }
}
