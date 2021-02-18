package com.bol.kalaha.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

import com.bol.kalaha.model.type.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private boolean isTurn;

    @Column
    private PlayerType type;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_id")
    private List<Pit> pits;
}
