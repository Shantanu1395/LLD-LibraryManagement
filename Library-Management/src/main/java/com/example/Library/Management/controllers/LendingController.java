package com.example.Library.Management.controllers;

import com.example.Library.Management.models.Fine;
import com.example.Library.Management.services.AccountService;
import com.example.Library.Management.services.LendingService;
import com.example.Library.Management.services.LibraryService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LendingController {

    private final LendingService lendingService;

    public String createLendingTransaction(String libraryCardId, String bookId, LocalDate dateOfBooking){
        return lendingService.createLending(libraryCardId, bookId, dateOfBooking).getLendingId();
    }

    public List<String> getFineTransaction(){
        return lendingService.getFineTransactions().stream().map(Fine::getFineId).collect(Collectors.toList());
    }

}
