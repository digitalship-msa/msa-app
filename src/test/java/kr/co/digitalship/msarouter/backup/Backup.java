package kr.co.digitalship.msarouter.backup;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.digitalship.msarouter.properties.DomainProperties;
import kr.co.digitalship.msarouter.subscribe.RequestFacade;
import kr.co.digitalship.msarouter.subscribe.model.BackupData;
import kr.co.digitalship.msarouter.subscribe.model.SimpleApiResponseData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;

@SpringBootTest
public class Backup {

    @Autowired
    RequestFacade facade;

    @Autowired
    DomainProperties domain;

    @Test
    public void backup() throws URISyntaxException {

        DomainProperties.Backup backup = domain.getBackup();

        SimpleApiResponseData response = facade.send(
                backup.getDomain(),
                backup.getPort(),
                backup.getEndpoint(),
                HttpMethod.valueOf(backup.getMethod()),
                new BackupData(10, "Rest Api", "2025-02-13T10:15:00"));

        Assertions.assertThat(response.code() / 100).isEqualTo(2);

    }
}
