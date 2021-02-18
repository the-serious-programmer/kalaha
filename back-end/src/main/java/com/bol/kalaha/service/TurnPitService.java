package com.bol.kalaha.service;

import java.util.List;
import java.util.UUID;

import com.bol.kalaha.exception.PitException;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.service.validate.PitValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.bol.kalaha.exception.PitException.NOT_FOUND;

@Service
@AllArgsConstructor
public class TurnPitService {

    public void setTurnPit(final UUID pitId, final List<Pit> pits) {
        final Pit turnPit = pits.stream()
            .filter(pit -> pit.getId().equals(pitId))
            .filter(PitValidation::isRegularPit)
            .filter(pit -> !PitValidation.isEmpty(pit))
            .findAny()
            .orElseThrow(() -> new PitException(NOT_FOUND));
        turnPit.setTurnPit(true);
    }

    public Pit getTurnPit(final List<Pit> pits) {
        return pits.stream()
            .filter(Pit::isTurnPit)
            .findAny()
            .orElseThrow(() -> new PitException(NOT_FOUND));
    }
}
