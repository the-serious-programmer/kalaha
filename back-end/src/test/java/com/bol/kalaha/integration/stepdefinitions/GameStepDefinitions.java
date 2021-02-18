package com.bol.kalaha.integration.stepdefinitions;

import java.util.List;
import java.util.UUID;

import com.bol.kalaha.KalahaApplication;
import com.bol.kalaha.dto.GameDTO;
import com.bol.kalaha.dto.PitDTO;
import com.bol.kalaha.dto.PlayerDTO;
import com.bol.kalaha.integration.httpclient.GameHttpClient;
import com.bol.kalaha.model.type.PitType;
import io.cucumber.java8.En;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpStatusCodeException;

import static org.junit.Assert.assertEquals;

@Slf4j
@SpringBootTest(
    classes = KalahaApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@CucumberContextConfiguration
public class GameStepDefinitions implements En {
    private static final int NO_STONES = 0;

    @Autowired
    private GameHttpClient gameHttpClient;

    private ResponseEntity<GameDTO> responseEntity;
    private int statusCode;
    private GameDTO result;

    public GameStepDefinitions() {
        When("user requests a new game", this::createGame);
        Then("should have response status {int}", (final Integer expectedResponseStatus) ->
            assertEquals((int) expectedResponseStatus, statusCode));
        When("player takes turn by selecting game, player and pit", this::takeTurn);
        When("player takes turn by selecting not existing game {string}, player {string} and pit {string}",
            this::takeTurnWith);
    }

    private void createGame() {
        this.responseEntity = gameHttpClient.post();
        this.statusCode = responseEntity.getStatusCodeValue();
        this.result = responseEntity.getBody();
    }

    private void takeTurnWith(final String gameIdText, final String playerIdText, final String pitIdText) {
        this.createGame();

        final UUID gameId = UUID.fromString(gameIdText);
        final UUID playerId = UUID.fromString(playerIdText);
        final UUID pitId = UUID.fromString(pitIdText);

        try {
            processResponseEntity(gameHttpClient.put(gameId, playerId, pitId));
        } catch (final HttpStatusCodeException e) {
            log.info("Caught HttpStatusCodeException during GameStepDefinitions: ", e);
            statusCode = e.getStatusCode().value();
        }
    }

    private void takeTurn() {
        this.createGame();

        final UUID gameId = result.getId();
        final List<PlayerDTO> players = result.getPlayers();
        final PlayerDTO currentPlayer = getActivePlayer(players);
        final UUID playerId = currentPlayer.getId();
        final UUID pitId = getRegularPitWithStones(currentPlayer.getPits()).getId();

        processResponseEntity(gameHttpClient.put(gameId, playerId, pitId));
    }

    private PlayerDTO getActivePlayer(final List<PlayerDTO> players) {
        return players
            .stream()
            .filter(PlayerDTO::isTurn)
            .findAny()
            .orElseThrow();
    }

    private PitDTO getRegularPitWithStones(final List<PitDTO> pits) {
        return pits
            .stream()
            .filter(pit -> pit.getType() == PitType.REGULAR)
            .filter(pit -> pit.getStones() > NO_STONES)
            .findAny()
            .orElseThrow();
    }

    private void processResponseEntity(final ResponseEntity<GameDTO> response) {
        this.responseEntity = response;
        this.statusCode = response.getStatusCodeValue();
        this.result = response.getBody();
    }

}
