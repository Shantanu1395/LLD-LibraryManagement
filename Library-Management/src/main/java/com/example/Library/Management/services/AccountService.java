package com.example.Library.Management.services;

import com.example.Library.Management.enums.AccountStatus;
import com.example.Library.Management.enums.LibraryCardStatus;
import com.example.Library.Management.enums.LibraryCardType;
import com.example.Library.Management.exceptions.LibraryCardNotFoundException;
import com.example.Library.Management.models.*;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class AccountService {

    private Map<String, Account> members = new HashMap<>();
    private Map<String, Account> librarians = new HashMap<>();

    private Map<String, LibraryCard> libraryCards = new HashMap<>();

    public Account createMember(@NonNull final String personName,@NonNull final LibraryCardType libraryCardType){
        String id = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        Account member = new Member(id, password, AccountStatus.ACTIVE, new Person(personName, "", "", ""));
        if(libraryCardType.equals(LibraryCardType.MEMBER))
            members.put(id, member);
        else
            librarians.put(id, member);
        return member;
    }

    /**
     * TODO -
     * Add create librarian
     * Add field in library card to show who created library card
     * */

    public LibraryCard createLibraryCard(@NonNull final String personId,@NonNull final LibraryCardType libraryCardType){
        Account person;
        if(libraryCardType.equals(LibraryCardType.MEMBER)){
            person = members.get(personId);
        }else{
            person = librarians.get(personId);
        }
        String library_id = UUID.randomUUID().toString();
        String barcode = UUID.randomUUID().toString();
        LibraryCard libraryCard = new LibraryCard(library_id, person, LibraryCardType.MEMBER, new Date(), barcode, LibraryCardStatus.ACTIVE);
        libraryCards.put(library_id, libraryCard);
        return libraryCard;
    }

    public Account getMemberAccountById(String accountId) {
        return members.get(accountId);
    }

    public List<LibraryCard> getMemberLibraryCards() {
        return libraryCards.values().stream().collect(Collectors.toList());
    }

    public LibraryCard getLibraryCardById(String libraryCardId){
        if(libraryCards.containsKey(libraryCardId)){
            return libraryCards.get(libraryCardId);
        }else{
            throw new LibraryCardNotFoundException("Library card does not exist");
        }
    }
}
