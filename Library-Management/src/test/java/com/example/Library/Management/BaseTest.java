package com.example.Library.Management;

import com.example.Library.Management.controllers.AccountController;
import com.example.Library.Management.controllers.LibraryController;
import com.example.Library.Management.enums.LibraryCardType;
import com.example.Library.Management.models.Account;
import com.example.Library.Management.models.Library;
import com.example.Library.Management.services.AccountService;
import com.example.Library.Management.services.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class BaseTest {

    LibraryController libraryController;
    AccountController accountController;

    public void setUpControllers(){

        LibraryService libraryService = new LibraryService();
        libraryController = new LibraryController(libraryService);

        AccountService accountService = new AccountService();
        accountController = new AccountController(accountService);

    }

    public String createLibraryInfra(){

        setUpControllers();

        String libraryId = libraryController.createLibrary("TestLibrary");

        String rack1 = libraryController.createRack("A", libraryId);
        String rack2 = libraryController.createRack("B", libraryId);

        String book1 = libraryController.createBook("Alchemist", "Paulo Coelho", rack1);
        String book2 = libraryController.createBook("Alice in wonderland", "Lewis Carol", rack1);
        String book3 = libraryController.createBook("A life of SIN", "Ricardo", rack1);

        String book4 = libraryController.createBook("Bob Marley", "Bob Marley", rack2);
        String book5 = libraryController.createBook("Bojack Horseman", "Raphael Bob-Waksberg", rack2);

        return libraryId;
    }

    public List<String> createAccountMember() {

        setUpControllers();

        String member1 = accountController.createMember("Shantanu", LibraryCardType.MEMBER);
        String member1LibraryCard = accountController.createLibraryCard(member1, LibraryCardType.MEMBER);

        String member2 = accountController.createMember("Rahul", LibraryCardType.MEMBER);
        String member2LibraryCard = accountController.createLibraryCard(member2, LibraryCardType.MEMBER);

        return Arrays.asList(member1LibraryCard, member2LibraryCard);
    }

    @Test
    public void testInfra(){
        String libraryId = createLibraryInfra();
        Library library = libraryController.getLibrary(libraryId);
        Assert.assertEquals(library.getRacks().size(), 2);
        Assert.assertEquals(library.getRacks().get(0).getBooks().size(), 3);
        Assert.assertEquals(library.getRacks().get(1).getBooks().size(), 2);
    }

    @Test
    public void testMemberAccountCreation(){

        List<String> accountMembers = createAccountMember();
        Assert.assertEquals(accountMembers.size(), accountController.getMemberAccounts().size());
    }

    @Test
    public void testBookLending(){
        String libraryId = createLibraryInfra();
        Library library = libraryController.getLibrary(libraryId);

        List<String> accountMembers = createAccountMember();
        List<Account> accounts = accountController.getMemberAccounts();

        //TODO
        /**
         * Lend some books to a member
         * Assert book status
         * Assert book lended to person
         * */

        /**
         * Lend a book to a member at some previous date
         * Assert fine
         * Assert entry being added to fine datastore
         * */

        /**
         * If there is a fine and it is more than 30 days since issue, block account & set library card status to blocked
         * Assert account status to BLOCKED
         * */

    }

}
