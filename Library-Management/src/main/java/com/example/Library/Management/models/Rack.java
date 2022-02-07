package com.example.Library.Management.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Rack {
    private String rackId;
    private String rackName;
    private List<BookItem> books;

    public Rack(String rackId, String rackName){
        this.rackId = rackId;
        this.rackName = rackName;
        books = new ArrayList();
    }

    public void addBookToRack(BookItem book){
        this.books.add(book);
    }

}
