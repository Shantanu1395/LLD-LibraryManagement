package com.example.Library.Management.exceptions;

public class UnableToLendAlreadyBurrowed extends RuntimeException{
    public UnableToLendAlreadyBurrowed(String s){
        super(s);
    }
}
