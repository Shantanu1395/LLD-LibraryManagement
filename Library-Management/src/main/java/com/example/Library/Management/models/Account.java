package com.example.Library.Management.models;

import com.example.Library.Management.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Account{
    private String id;
    private String password;
    private AccountStatus accountStatus;
    private Person person;
}
