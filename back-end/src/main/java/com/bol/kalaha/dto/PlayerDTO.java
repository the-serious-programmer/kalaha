package com.bol.kalaha.dto;

import java.util.List;
import java.util.UUID;

import com.bol.kalaha.model.type.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDTO {

    @NonNull
    private UUID id;

    @NonNull
    private PlayerType type;

    private boolean turn;

    private List<PitDTO> pits;
}
