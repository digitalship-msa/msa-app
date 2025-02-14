package kr.co.digitalship.msarouter.save;

import kr.co.digitalship.msarouter.aspect.SaveLog;
import kr.co.digitalship.msarouter.log.LogApplication;
import kr.co.digitalship.msarouter.properties.DomainProperties;
import kr.co.digitalship.msarouter.save.model.InputTextDTO;
import kr.co.digitalship.msarouter.save.model.InputTextData;
import kr.co.digitalship.msarouter.save.model.SimpleResponseVO;
import kr.co.digitalship.msarouter.subscribe.DefaultRequestFacade;
import kr.co.digitalship.msarouter.subscribe.RequestFacade;
import kr.co.digitalship.msarouter.subscribe.model.SimpleApiResponseData;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;


import java.net.URISyntaxException;


@Service
public class SaveTextService {

    // Discovery
    private final DomainProperties domain;

    private final RequestFacade facade;

    private final LogApplication log;

    public SaveTextService(DomainProperties domain, DefaultRequestFacade facade, LogApplication log) {
        this.domain = domain;
        this.facade = facade;
        this.log = log;
    }

    @SaveLog(before = "Send to MSA server", after = "Response: OK")
    public SimpleResponseVO save(InputTextData data) {
        DomainProperties.Save save = domain.getSave();
        try {
            SimpleApiResponseData res = facade.send(
                    save.getDomain(),
                    save.getPort(),
                    save.getEndpoint(),
                    HttpMethod.valueOf(save.getMethod().toUpperCase()),
                    new InputTextDTO(data.data()));

            int code = res.code();
            String responseBody = res.body();
            log.saveLog("Response Body: " + responseBody);
            if (code / 100 != 2) throw new RuntimeException();

            return new SimpleResponseVO(1);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
