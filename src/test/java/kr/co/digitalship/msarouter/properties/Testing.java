package kr.co.digitalship.msarouter.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Testing {

    @Autowired
    DomainProperties properties;

    @Test
    public void test() {
        Assertions.assertNotNull(properties.getSave().getDomain());
    }
}
