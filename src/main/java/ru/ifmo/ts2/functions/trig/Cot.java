package ru.ifmo.ts2.functions.trig;

import lombok.RequiredArgsConstructor;
import ru.ifmo.ts2.report.CsvResultSaver;

import java.util.function.DoubleFunction;

@RequiredArgsConstructor
public class Cot implements DoubleFunction<Double> {

    private final Cos cos;
    private final Sin sin;
    private final CsvResultSaver saver;

    @Override
    public Double apply(double value) {
        var sinValue = sin.apply(value);
        var cosValue = cos.apply(value);
        if (Double.compare(sinValue, 0.0) == 0 ||
                Double.compare(sinValue, -0.0) == 0) {
            throw new ArithmeticException("Value out of range: " + value);
        }
        double res = cosValue / sinValue;
        saver.saveResult(String.format("cot(%.3f)", value), res);
        return res;
    }
}
