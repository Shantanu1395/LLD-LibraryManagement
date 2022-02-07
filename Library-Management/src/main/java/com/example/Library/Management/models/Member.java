package com.example.Library.Management.models;

import com.example.Library.Management.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Member extends Account{
    public Member(String id, String password, AccountStatus accountStatus, Person person) {
        super(id, password, accountStatus, person);
    }
}
