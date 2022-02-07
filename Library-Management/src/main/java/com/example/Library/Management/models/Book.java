package com.example.Library.Management.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {
    private String isbn;
    private String bookName;
    private String author;
    /*
    Other metadata
    private String publisher;
    private String pages;
    private String language;
    private Date publicationDate;
    private Date purchaseDate;
    private int price;
    * */
}
