package com.example.Library.Management.exceptions;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String s){
        super(s);
    }
}
