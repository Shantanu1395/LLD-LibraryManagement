package com.example.Library.Management.models;

import com.example.Library.Management.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Librarian extends Account{
    public Librarian(String id, String password, AccountStatus accountStatus, Person person) {
        super(id, password, accountStatus, person);
    }
}
