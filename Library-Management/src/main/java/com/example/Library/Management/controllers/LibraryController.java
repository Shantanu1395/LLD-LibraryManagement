package com.example.Library.Management.controllers;

import com.example.Library.Management.models.Library;
import com.example.Library.Management.services.LibraryService;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class LibraryController {

    private LibraryService libraryService;

    public String createLibrary(@NonNull final String libraryName){
        return libraryService.createLibrary(libraryName).getLibraryId();
    }

    public String createRack(String rackName, String libraryId){
        return this.libraryService.createRack(rackName, libraryId).getRackId();
    }

    public String createBook(String bookName, String author, String rackId){
        return this.libraryService.createBook(bookName, author, rackId).getBookId();
    }

    public Library getLibrary(String libraryId){
        return this.libraryService.getLibrary(libraryId);
    }

}
