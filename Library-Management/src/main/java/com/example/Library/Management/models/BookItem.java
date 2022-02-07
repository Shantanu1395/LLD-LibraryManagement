package com.example.Library.Management.models;

import com.example.Library.Management.enums.BookStatus;
import lombok.Getter;

import java.util.Date;

@Getter
public class BookItem extends Book{

    private String bookId;
    private String lastBurrowedById;
    private Date lastBurrowedOn;
    private Date returnDueDate;
    private BookStatus bookStatus;
    private String barcode;
    private Rack rack;

    public BookItem(String isbn, String bookName, String author, String bookId, String lastBurrowedById, Date lastBurrowedOn, Date returnDueDate, BookStatus bookStatus, String barcode, Rack rack) {
        super(isbn, bookName, author);
        this.bookId = bookId;
        this.lastBurrowedById = lastBurrowedById;
        this.lastBurrowedOn = lastBurrowedOn;
        this.returnDueDate = returnDueDate;
        this.bookStatus = bookStatus;
        this.barcode = barcode;
        this.rack = rack;
    }

    public void setBookStatus(BookStatus bookStatus){
        this.bookStatus = bookStatus;
    }

}
