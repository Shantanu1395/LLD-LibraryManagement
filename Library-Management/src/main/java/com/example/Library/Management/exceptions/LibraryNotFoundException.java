package com.example.Library.Management.exceptions;

public class LibraryNotFoundException extends RuntimeException{
    public LibraryNotFoundException(String exception){
        super(exception);
    }
}
