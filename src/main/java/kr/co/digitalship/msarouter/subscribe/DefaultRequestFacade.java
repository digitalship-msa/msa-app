package kr.co.digitalship.msarouter.subscribe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.digitalship.msarouter.subscribe.model.SimpleApiResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class DefaultRequestFacade implements RequestFacade {

    private final ObjectMapper om;

    // API 보내기
    public <T> SimpleApiResponseData send(String ip, int port, String endpoint, HttpMethod method, T message) throws URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(new URI(ip + (port > 0 ? (":" + port) : "") + (endpoint != null ? endpoint : "")));

        if (method == HttpMethod.GET) {
            requestBuilder.GET();
        } else if (method == HttpMethod.POST) {
            try {
                requestBuilder.POST(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(message)));
                requestBuilder.header("Content-Type", "application/json");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        int code = response.statusCode();
        String responseBody = response.body();

        if (code / 100 != 2) {
            throw new RuntimeException();
        }
        return new SimpleApiResponseData(code, responseBody);
    }

    public <T> SimpleApiResponseData send(String ip, int port, String endpoint, T message) throws URISyntaxException {
        return send(ip, port, endpoint, message == null ? HttpMethod.GET : HttpMethod.POST, message);
    }

    public <T, R> R send(String ip, int port, String endpoint, T message, Class<R> responseType) throws URISyntaxException {
        return send(ip, port, endpoint, message == null ? HttpMethod.GET : HttpMethod.POST, message, responseType);
    }

    public <T, R> R send(String ip, int port, String endpoint, HttpMethod method, T message, Class<R> responseType) throws URISyntaxException {
        SimpleApiResponseData response = send(ip, port, endpoint, method, message);
        try {
            return om.readValue(response.body(), responseType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
