package ru.ifmo.ts2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.ts2.functions.log.Ln;
import ru.ifmo.ts2.functions.log.Log10;
import ru.ifmo.ts2.functions.log.Log2;
import ru.ifmo.ts2.functions.log.Log3;
import ru.ifmo.ts2.functions.log.Log5;
import ru.ifmo.ts2.functions.trig.Cos;
import ru.ifmo.ts2.functions.trig.Cot;
import ru.ifmo.ts2.functions.trig.Sec;
import ru.ifmo.ts2.functions.trig.Sin;
import ru.ifmo.ts2.functions.trig.Tan;
import ru.ifmo.ts2.report.CsvResultSaver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleFunction;

@Configuration
public class AppConfig {

    @Bean
    public CsvResultSaver csvResultSaver() {
        return new CsvResultSaver();
    }

    @Bean
    public Ln ln(CsvResultSaver saver) {
        return new Ln(saver);
    }

    @Bean
    public Log2 log2(Ln ln, CsvResultSaver saver) {
        return new Log2(ln, saver);
    }

    @Bean
    public Log3 log3(Ln ln, CsvResultSaver saver) {
        return new Log3(ln, saver);
    }

    @Bean
    public Log5 log5(Ln ln, CsvResultSaver saver) {
        return new Log5(ln, saver);
    }

    @Bean
    public Log10 log10(Ln ln, CsvResultSaver saver) {
        return new Log10(ln, saver);
    }

    @Bean
    public Sin sin(CsvResultSaver saver, Cos cos) {
        return new Sin(cos, saver);
    }

    @Bean
    public Cos cos(CsvResultSaver saver) {
        return new Cos(saver);
    }

    @Bean
    public Tan tan(Sin sin, Cos cos, CsvResultSaver saver) {
        return new Tan(cos, sin, saver);
    }

    @Bean
    public Cot cot(Sin sin, Cos cos, CsvResultSaver saver) {
        return new Cot(cos, sin, saver);
    }

    @Bean
    public Sec sec(Cos cos, CsvResultSaver saver) {
        return new Sec(cos, saver);
    }

    @Bean
    public HashMap<String, DoubleFunction<Double>> fns(Map<String, DoubleFunction<Double>> nameToFunctionMap) {
        return new HashMap<>(nameToFunctionMap);
    }
}
