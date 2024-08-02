package practice_project.project.utils;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
public class StaPath {
    @Value("${spring.mvc.static-path-pattern}")
    private String path;
}
