package ru.ifmo.ts2.functions.trig;

import lombok.RequiredArgsConstructor;
import ru.ifmo.ts2.report.CsvResultSaver;

import java.util.function.DoubleFunction;

@RequiredArgsConstructor
public class Sin implements DoubleFunction<Double> {

    private final Cos cos;
    private final CsvResultSaver saver;

    @Override
    public Double apply(double value) {
        double cosValue = cos.apply(value);
        double sineSquared = 1 - Math.pow(cosValue, 2);
        double normalizedAngle = value % (2 * Math.PI);
        if (normalizedAngle < 0) {
            normalizedAngle += 2 * Math.PI;
        }
        boolean isPositive = (normalizedAngle >= 0 && normalizedAngle <= Math.PI);
        double result = isPositive ? Math.sqrt(sineSquared) : -Math.sqrt(sineSquared);
        saver.saveResult(String.format("sin(%.3f)", value), result);
        return result;
    }
}
