package kr.co.digitalship.msarouter.save;

import kr.co.digitalship.msarouter.save.model.InputTextData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Saving {

    @Autowired
    SaveTextService service;

    @Test
    public void save() {
        service.save(new InputTextData("hello world."));
    }

}
