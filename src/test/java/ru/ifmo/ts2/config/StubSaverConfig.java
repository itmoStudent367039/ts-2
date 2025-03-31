package ru.ifmo.ts2.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.ifmo.ts2.report.CsvResultSaver;

@TestConfiguration
public class StubSaverConfig {

    @Bean
    CsvResultSaver saver() {
        return Mockito.mock(CsvResultSaver.class);
    }
}
