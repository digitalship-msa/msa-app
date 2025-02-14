package kr.co.digitalship.msarouter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "msa.data")
@Getter
@Setter
public class DomainProperties {

    private Save save;
    private Backup backup;

    @Getter
    @Setter
    public static class Save {
        private String domain;
        private int port;
        private String method;
        private String endpoint;
    }

    @Getter
    @Setter
    public static class Backup {
        private String domain;
        private int port;
        private String method;
        private String endpoint;
        private int retryCount;
    }
}
