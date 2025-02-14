package kr.co.digitalship.msarouter.subscribe;

import kr.co.digitalship.msarouter.subscribe.model.SimpleApiResponseData;
import org.springframework.http.HttpMethod;

import java.net.URISyntaxException;

public interface RequestFacade {
    <T> SimpleApiResponseData send(String ip, int port, String endpoint, HttpMethod method, T message) throws URISyntaxException;

    <T> SimpleApiResponseData send(String ip, int port, String endpoint, T message) throws URISyntaxException;

    // Generate Object 'R' by Response Body
    <T, R> R send(String ip, int port, String endpoint, T message, Class<R> responseType) throws URISyntaxException;

    // Generate Object 'R' by Response Body
    <T, R> R send(String ip, int port, String endpoint, HttpMethod method, T message, Class<R> responseType) throws URISyntaxException;

}
