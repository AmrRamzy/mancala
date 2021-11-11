package com.bol.mancala.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameBoard {

    private final String playerName;
    private int[] board = {6,6,6,6,6,6};
    private int mancala;

    public void addToMancala(int stones) {
        this.mancala+=stones;
    }
}