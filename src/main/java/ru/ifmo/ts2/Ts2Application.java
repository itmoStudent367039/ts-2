package ru.ifmo.ts2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.ifmo.ts2.config.properties.InputProperties;
import ru.ifmo.ts2.report.CsvResultSaver;

@SpringBootApplication
@EnableConfigurationProperties({
        InputProperties.class
})
public class Ts2Application {

    public static void main(String[] args) {
        CsvResultSaver saver = null;
        try (var context = SpringApplication.run(Ts2Application.class, args)) {
            var system = context.getBean(SystemSolver.class);
            var input = context.getBean(InputProperties.class);
            saver = context.getBean(CsvResultSaver.class);
            var res = system.calculate(input.getValue());
            saver.saveResult("base_system", res);
        } finally {
            if (saver != null) {
                saver.writeResultsToCsv();
            }
        }
    }
}
