package practice_project.project.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "server")
public class LocalOSSProperties {
    private String address;
    private int port;
}