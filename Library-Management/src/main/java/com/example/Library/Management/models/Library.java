package com.example.Library.Management.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Library {
    private String libraryId;
    private String name;
    private List<Rack> racks;
    private List<BookItem> books;

    //TODO - Add later
    //private List<Account> accounts;

    public Library(String libraryId, String name){
        this.libraryId = libraryId;
        this.name = name;
        this.racks = new ArrayList();
        this.books = new ArrayList();
    }

    public void addBookToLibrary(BookItem book){
        this.books.add(book);
    }

    public void addRacksToLibrary(Rack rack){
        this.racks.add(rack);
    }
}
