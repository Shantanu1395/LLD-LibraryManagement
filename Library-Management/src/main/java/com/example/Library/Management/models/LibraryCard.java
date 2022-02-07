package com.example.Library.Management.models;

import com.example.Library.Management.enums.LibraryCardStatus;
import com.example.Library.Management.enums.LibraryCardType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class LibraryCard {
    private String cardId;
    private Account account;
    private LibraryCardType libraryCardType;
    private Date issued_at;
    private String barcode;
    private LibraryCardStatus libraryCardStatus;
}
