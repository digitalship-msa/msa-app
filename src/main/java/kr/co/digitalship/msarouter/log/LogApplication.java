package kr.co.digitalship.msarouter.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class LogApplication {

    private final Map<String, String> logRepository = new LinkedHashMap<>();

    public int saveLog(String log) {
        logRepository.put(String.valueOf(LocalDateTime.now()), log);
        return logRepository.size();
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void log() {
        log.info("===== server status: up =====");
        for (String k : logRepository.keySet()) {
            log.info("\nlog at {}\nmessage: {}\n", k, logRepository.get(k));
        }
        log.info("=============================");
    }
}
