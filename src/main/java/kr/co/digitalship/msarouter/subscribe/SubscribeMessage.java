package kr.co.digitalship.msarouter.subscribe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.digitalship.msarouter.log.LogApplication;
import kr.co.digitalship.msarouter.properties.DomainProperties;
import kr.co.digitalship.msarouter.subscribe.model.BackupData;
import kr.co.digitalship.msarouter.subscribe.model.SimpleApiResponseData;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class SubscribeMessage {

    private final RequestFacade facade;

    // Log Monitor
    private final LogApplication log;

    private final DomainProperties domain;

    private final ObjectMapper om;

    @KafkaListener(topics = {"ukha"})
    public void sub(ConsumerRecord<String, String> msg, Acknowledgment ack, Consumer<?, ?> consumer) {
        String[] val;
        try {
            val = om.readValue(msg.value(), String[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.saveLog("Event received! : " + val);
        DomainProperties.Backup backup = domain.getBackup();
        int retryCnt = backup.getRetryCount();
        log.saveLog("Request to Backup server! - start");
        for (String v : val) {
            for (int i = 1; i <= retryCnt; i++) {
                try {
                    SimpleApiResponseData response = facade.send(
                            backup.getDomain(),
                            backup.getPort(),
                            backup.getEndpoint(),
                            HttpMethod.valueOf(backup.getMethod()),
                            om.readValue(v, BackupData.class));
                    log.saveLog("Request to Backup server! - done, response: \n" + response.body() + "\n");
                    ack.acknowledge();
                    break;
                } catch (URISyntaxException e) {
                    if (i < retryCnt) log.saveLog("ERROR! === again ===\nRequest to Backup server! - restart");
                    else {
                        log.saveLog("ERROR! fail backup.. add to queue this Task...");
                        TopicPartition tp = new TopicPartition(msg.topic(), msg.partition());
                        consumer.seek(tp, msg.offset());
                        throw new RuntimeException(e);
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
