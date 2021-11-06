package com.bol.mancala.entity.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {

    @Id
    private String name;
    private int[] board = {6,6,6,6,6,6};
    private int mancala;

    public void addToMancala(int stones) {
        this.mancala+=stones;
    }
}
