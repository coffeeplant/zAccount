package com.zaccount.model;

import lombok.*;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Component
public class Atm {

    private int availableFunds;
    private LinkedHashMap<Integer, Integer> notes;

    public Atm(){
        this.availableFunds=1500;
        this.notes = new LinkedHashMap<>();
        notes.put(50, 10);
        notes.put(20,30);
        notes.put(10, 30);
        notes.put(5, 20);
    }


}
