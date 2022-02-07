package com.example.Library.Management.models;

import com.example.Library.Management.enums.LendingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
public class LendTransaction {
    private String lendingId;
    private BookItem bookItem;
    private LibraryCard libraryCard;
    private LocalDate issuedOn;
    private LocalDate expiryDate;
    private LendingStatus lendingStatus;

    public void setLendingStatus(LendingStatus lendingStatus){
        this.lendingStatus = lendingStatus;
    }

}
