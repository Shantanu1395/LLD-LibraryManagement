package com.example.Library.Management.services;

import com.example.Library.Management.enums.BookStatus;
import com.example.Library.Management.exceptions.LibraryNotFoundException;
import com.example.Library.Management.models.BookItem;
import com.example.Library.Management.models.Library;
import com.example.Library.Management.models.Rack;
import lombok.NonNull;
import org.springframework.http.server.DelegatingServerHttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LibraryService {

    private final Map<String, Library> libraries = new HashMap<>();
    private final Map<String, Rack> racks = new HashMap<>();
    private final Map<String, BookItem> books = new HashMap<>();

    public Library createLibrary(@NonNull final String libraryName){
        String libraryID = UUID.randomUUID().toString();
        Library library = new Library(libraryID, libraryName);
        this.libraries.put(libraryID, library);
        return library;
    }

    public Rack createRack(@NonNull final String rackName,@NonNull final String libraryId){
        Library library = libraries.get(libraryId);
        String rackID = UUID.randomUUID().toString();
        Rack rack = new Rack(rackID, rackName);
        library.addRacksToLibrary(rack);
        this.racks.put(rackID, rack);
        return rack;
    }

    public BookItem createBook(@NonNull final String bookName,@NonNull final String author,@NonNull final String rackId){
        Rack rack = racks.get(rackId);
        String isbn = UUID.randomUUID().toString();
        String barcode = UUID.randomUUID().toString();
        String bookID = UUID.randomUUID().toString();
        BookItem book = new BookItem(isbn, bookName, author, bookID, null, null , null, BookStatus.AVAILABLE, barcode, rack);
        rack.addBookToRack(book);
        books.put(bookID, book);
        return book;
    }

    public Library getLibrary(String libraryId){
        if (libraries.containsKey(libraryId)){
            return libraries.get(libraryId);
        }
        else{
            throw new LibraryNotFoundException("No such library found");
        }
    }

}
