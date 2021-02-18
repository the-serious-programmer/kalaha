package com.bol.kalaha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.UUID;

import com.bol.kalaha.model.type.PitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pit {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private Integer position;

    @Enumerated(EnumType.STRING)
    private PitType type;

    @Column
    private int stones;

    @Transient
    private boolean lastSowedPit;

    @Transient
    private boolean turnPit;
}
