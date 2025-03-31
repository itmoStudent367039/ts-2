package ru.ifmo.ts2.functions.log;

import lombok.RequiredArgsConstructor;
import ru.ifmo.ts2.report.CsvResultSaver;

import java.util.function.DoubleFunction;

@RequiredArgsConstructor
public class Log10 implements DoubleFunction<Double> {

    private final Ln ln;
    private final CsvResultSaver saver;

    @Override
    public Double apply(double value) {
        double res = ln.apply(value) / ln.apply(10);
        saver.saveResult(String.format("log10(%.3f)", value), res);
        return res;
    }
}
