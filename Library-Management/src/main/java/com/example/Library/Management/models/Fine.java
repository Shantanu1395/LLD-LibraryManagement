package com.example.Library.Management.models;

import com.example.Library.Management.enums.FineStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Fine {
    private String fineId;
    private LendTransaction lendTransaction;
    private int amount;
    private FineStatus fineStatus;
    private LocalDate issuedOn;

    public void setFineStatus(FineStatus fineStatus){
        this.fineStatus = fineStatus;
    }

}
