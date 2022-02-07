package com.example.Library.Management.exceptions;

public class LendQuotaExceededException extends RuntimeException{
    public LendQuotaExceededException(String e){
        super(e);
    }
}
