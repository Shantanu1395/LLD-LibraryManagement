package com.example.Library.Management.models;

import com.example.Library.Management.enums.LibraryCardStatus;
import com.example.Library.Management.enums.LibraryCardType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class LibraryCard {
    private String cardId;
    private Account account;
    private LibraryCardType libraryCardType;
    private Date issued_at;
    private String barcode;
    private LibraryCardStatus libraryCardStatus;
    private List<LendTransaction> lendTransactionList;

    public LibraryCard(String cardId, Account account, LibraryCardType libraryCardType, Date issued_at, String barcode, LibraryCardStatus libraryCardStatus) {
        this.cardId = cardId;
        this.account = account;
        this.libraryCardType = libraryCardType;
        this.issued_at = issued_at;
        this.barcode = barcode;
        this.libraryCardStatus = libraryCardStatus;
        this.lendTransactionList = new ArrayList<>();
    }

    public void addLendingTransactionToLibraryCard(LendTransaction lendTransaction){
        this.lendTransactionList.add(lendTransaction);
    }

    public void setLibraryCardStatus(LibraryCardStatus libraryCardStatus){
        this.libraryCardStatus = libraryCardStatus;
    }

}
