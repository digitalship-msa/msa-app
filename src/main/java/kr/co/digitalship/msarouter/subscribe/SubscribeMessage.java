package kr.co.digitalship.msarouter.subscribe;

import kr.co.digitalship.msarouter.log.LogApplication;
import kr.co.digitalship.msarouter.subscribe.model.BackupData;
import kr.co.digitalship.msarouter.subscribe.model.SimpleApiResponseData;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

//@Component
@RequiredArgsConstructor
public class SubscribeMessage {

    private final SubscribeMessageFacade facade;

    // Log Monitor
    private final LogApplication log;

    // Discovery
    private final String URI = "";
    // Discovery
    private final int PORT = 0;
    // Discovery
    private final HttpMethod method = HttpMethod.POST;

    @KafkaListener(topics = {"tp"})
    public void sub(ConsumerRecord<String, String> msg) {
        String key = msg.key();
        String val = msg.value();
        log.saveLog("Event received! : " + val);

        try {
            /* TODO 2025-02-10 월 16:50
                Backup Data 데이터 변경
                --by Hyunmin
            */
            log.saveLog("Request to Backup server! - start");
            SimpleApiResponseData response = facade.send(URI, PORT, method, new BackupData(key, val));
            log.saveLog("Request to Backup server! - done, response: \n" + response.body() + "\n");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
