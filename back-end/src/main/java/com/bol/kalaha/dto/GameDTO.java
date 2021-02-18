package com.bol.kalaha.dto;

import java.util.List;
import java.util.UUID;

import com.bol.kalaha.model.type.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDTO {

    @NonNull
    private UUID id;

    @NonNull
    private GameStatus status;

    private List<PlayerDTO> players;
}
