package kr.co.digitalship.msarouter.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.digitalship.msarouter.aspect.SaveLog;
import kr.co.digitalship.msarouter.log.LogApplication;
import kr.co.digitalship.msarouter.save.model.InputTextData;
import kr.co.digitalship.msarouter.save.model.SimpleResponseVO;
import kr.co.digitalship.msarouter.subscribe.DefaultSubscribeMessageFacade;
import kr.co.digitalship.msarouter.subscribe.SubscribeMessageFacade;
import kr.co.digitalship.msarouter.subscribe.model.SimpleApiResponseData;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;


import java.net.URISyntaxException;


@Service
public class SaveTextService {

    // Discovery
    private final String URI = "";
    // Discovery
    private final int PORT = 8080;
    // Discovery
    private final HttpMethod METHOD = HttpMethod.POST;

    private final SubscribeMessageFacade facade;

    private final LogApplication log;

    public SaveTextService(DefaultSubscribeMessageFacade facade, LogApplication log) {
        this.facade = facade;
        this.log = log;
    }

    @SaveLog(before = "Send to MSA server", after = "Response: OK")
    public SimpleResponseVO save(InputTextData data) {
        try {
            SimpleApiResponseData res = facade.send(URI, PORT, METHOD, data);

            int code = res.code();
            String responseBody = res.body();

            if (code / 100 != 2) throw new RuntimeException();

            return new SimpleResponseVO(1);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
