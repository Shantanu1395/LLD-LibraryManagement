package com.example.Library.Management.controllers;

import com.example.Library.Management.enums.LibraryCardType;
import com.example.Library.Management.models.Account;
import com.example.Library.Management.models.LibraryCard;
import com.example.Library.Management.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
public class AccountController {

    AccountService accountService;

    public String createMember(@NonNull final String personName,@NonNull final LibraryCardType libraryCardType){
        return accountService.createMember(personName, libraryCardType).getId();
    }

    public String createLibraryCard(@NonNull final String personId,@NonNull final LibraryCardType libraryCardType){
        return accountService.createLibraryCard(personId, libraryCardType).getCardId();
    }

    public Account getMemberAccount(String accountId){
        return this.accountService.getMemberAccountById(accountId);
    }

    public List<Account> getMemberAccounts(){
        return accountService.getMemberAccounts();
    }

}
