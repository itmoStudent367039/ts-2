package ru.ifmo.ts2.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("input")
public class InputProperties {

    private Double value;
}
