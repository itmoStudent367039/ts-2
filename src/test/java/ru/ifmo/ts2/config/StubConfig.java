package ru.ifmo.ts2.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleFunction;

@TestConfiguration
public class StubConfig {

    @Bean
    public HashMap<String, DoubleFunction<Double>> nameToFunctionMap(Map<String, DoubleFunction<Double>> fnMap) {
        return new HashMap<>(fnMap);
    }
}
