package com.bol.kalaha.dto;

import java.util.UUID;

import com.bol.kalaha.model.type.PitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PitDTO {

    @NonNull
    private UUID id;

    @NonNull
    private int position;

    @NonNull
    private PitType type;

    @NonNull
    private int stones;
}
