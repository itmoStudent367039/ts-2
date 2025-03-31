package ru.ifmo.ts2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.function.DoubleFunction;

@Component
public class SystemSolver {

    @Value("${taylor.epsilon}")
    private Double epsilon;

    private final HashMap<String, DoubleFunction<Double>> nameToFunctionMap;

    public SystemSolver(HashMap<String, DoubleFunction<Double>> nameToFunctionMap) {
        this.nameToFunctionMap = nameToFunctionMap;
    }

    public double calculate(double value) {
        if (value <= 0) {
            return calculateFirst(value);
        } else {
            return calculateSecond(value);
        }
    }

    private double calculateSecond(double value) {
        double log2 = nameToFunctionMap.get("log2").apply(value);
        double log3 = nameToFunctionMap.get("log3").apply(value);
        double log5 = nameToFunctionMap.get("log5").apply(value);
        double log10 = nameToFunctionMap.get("log10").apply(value);

        if (Double.compare(Math.abs(log3 + log10), epsilon) < 0) {
            throw new ArithmeticException("Divide by zero in equation; value: " + value);
        }
        return (((log5 + log2) + (log10 * log10)) +
                (log3 * Math.pow(log3, 3)) +
                (log5 - ((log10 + log2) / log10))) /
                Math.pow((log10 + log3), 2);
    }

    private double calculateFirst(double value) {
        double tan = nameToFunctionMap.get("tan").apply(value);
        double cot = nameToFunctionMap.get("cot").apply(value);
        double cos = nameToFunctionMap.get("cos").apply(value);
        double sec = nameToFunctionMap.get("sec").apply(value);

        double part1 = Math.pow((((tan / cot) * cot) * cos), 3);
        return (part1 * (cos * tan)) * ((Math.pow(sec, 2)) / tan);
    }
}
