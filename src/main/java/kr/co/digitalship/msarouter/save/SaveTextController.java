package kr.co.digitalship.msarouter.save;

import kr.co.digitalship.msarouter.aspect.SaveLog;
import kr.co.digitalship.msarouter.log.LogApplication;
import kr.co.digitalship.msarouter.save.model.InputTextData;
import kr.co.digitalship.msarouter.save.model.SimpleResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class SaveTextController {

    private final SaveTextService service;

    private final LogApplication log;

    @SaveLog(before = "Request URL: /text")
    @PostMapping("/text")
    public SimpleResponseVO save(@RequestBody InputTextData data) {
        return service.save(data);
    }

}
