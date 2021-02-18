package com.bol.kalaha.integration.httpclient;

import java.util.UUID;

import com.bol.kalaha.dto.GameDTO;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class GameHttpClient {

    private static final String SERVER_URL = "http://localhost";
    private static final String ENDPOINT = "/api/game/";
    private static final String PLAYER_SUB_ENDPOINT = "/player/";
    private static final String PIT_SUB_ENDPOINT = "/pit/";
    private final RestTemplate restTemplate;
    @LocalServerPort
    private int port;

    public GameHttpClient() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<GameDTO> post() {
        final String url = getKalahaEndpoint();
        return restTemplate.postForEntity(url, null, GameDTO.class);
    }

    public ResponseEntity<GameDTO> put(final UUID gameId, final UUID playerId, final UUID pitId) {
        final String url = getKalahaEndpoint() + gameId + PLAYER_SUB_ENDPOINT + playerId + PIT_SUB_ENDPOINT + pitId;
        return restTemplate.exchange(url, HttpMethod.PUT, null, GameDTO.class);
    }

    private String getKalahaEndpoint() {
        return SERVER_URL + ":" + port + ENDPOINT;
    }
}
