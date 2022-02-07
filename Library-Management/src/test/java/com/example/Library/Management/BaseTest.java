package com.example.Library.Management;

import com.example.Library.Management.controllers.AccountController;
import com.example.Library.Management.controllers.LendingController;
import com.example.Library.Management.controllers.LibraryController;
import com.example.Library.Management.enums.BookStatus;
import com.example.Library.Management.enums.LibraryCardStatus;
import com.example.Library.Management.enums.LibraryCardType;
import com.example.Library.Management.exceptions.LendQuotaExceededException;
import com.example.Library.Management.exceptions.PreviousFineExistsException;
import com.example.Library.Management.exceptions.UnableToLendAlreadyBurrowed;
import com.example.Library.Management.models.Library;
import com.example.Library.Management.models.LibraryCard;
import com.example.Library.Management.services.AccountService;
import com.example.Library.Management.services.LendingService;
import com.example.Library.Management.services.LibraryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BaseTest {

    LibraryController libraryController;
    AccountController accountController;
    LendingController lendingController;

    public void setUpControllers(){

        LibraryService libraryService = new LibraryService();
        libraryController = new LibraryController(libraryService);

        AccountService accountService = new AccountService();
        accountController = new AccountController(accountService);

        LendingService lendingService = new LendingService(accountService, libraryService);
        lendingController = new LendingController(lendingService);

    }

    public String createLibraryInfra(){

        String libraryId = libraryController.createLibrary("TestLibrary");

        String rack1 = libraryController.createRack("A", libraryId);
        String rack2 = libraryController.createRack("B", libraryId);

        String book1 = libraryController.createBook("Alchemist", "Paulo Coelho", rack1);
        String book2 = libraryController.createBook("Alice in wonderland", "Lewis Carol", rack1);
        String book3 = libraryController.createBook("A life of SIN", "Ricardo", rack1);

        String book4 = libraryController.createBook("Bob Marley", "Bob Marley", rack2);
        String book5 = libraryController.createBook("Bojack Horseman", "Raphael Bob-Waksberg", rack2);
        String book6 = libraryController.createBook("Bojack Horseman 2", "Raphael Bob-Waksberg", rack2);

        return libraryId;
    }

    public List<String> createAccountMember() {

        String member1 = accountController.createMember("Shantanu", LibraryCardType.MEMBER);
        String member1LibraryCard = accountController.createLibraryCard(member1, LibraryCardType.MEMBER);

        String member2 = accountController.createMember("Rahul", LibraryCardType.MEMBER);
        String member2LibraryCard = accountController.createLibraryCard(member2, LibraryCardType.MEMBER);

        return Arrays.asList(member1LibraryCard, member2LibraryCard);
    }

    @Test
    public void testInfra(){
        setUpControllers();
        String libraryId = createLibraryInfra();
        Library library = libraryController.getLibrary(libraryId);
        Assertions.assertEquals(library.getRacks().size(), 2);
        Assertions.assertEquals(library.getRacks().get(0).getBooks().size(), 3);
        Assertions.assertEquals(library.getRacks().get(1).getBooks().size(), 3);
    }

    @Test
    public void testMemberAccountCreation(){
        setUpControllers();
        List<String> accountMembers = createAccountMember();
        Assertions.assertEquals(accountMembers.size(), accountController.getMemberLibraryCards().size());
    }

    /*
     * Testing borrowing already borrowed book
     * Testing fine on books post expiry date
     */
    @Test
    public void testBookLending(){
        setUpControllers();
        String libraryId = createLibraryInfra();
        Library library = libraryController.getLibrary(libraryId);

        List<String> accountMembers = createAccountMember();
        List<LibraryCard> libraryCards = accountController.getMemberLibraryCards();

        /*
         * Case 1-
         * Lend some books to a member
         * Assert book status
         * Assert count of book lend to person
         * */
        //Burrow a book
        lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(0).getBooks().get(0).getBookId(), LocalDate.now().minusDays(4));
        Assertions.assertEquals(library.getRacks().get(0).getBooks().get(0).getBookStatus(), BookStatus.BURROWED);
        Assertions.assertEquals(libraryCards.get(0).getLendTransactionList().size(), 1);

        //Burrow another book
        lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(0).getBooks().get(1).getBookId(), LocalDate.now().minusDays(6));
        Assertions.assertEquals(library.getRacks().get(0).getBooks().get(1).getBookStatus(), BookStatus.BURROWED);
        Assertions.assertEquals(libraryCards.get(0).getLendTransactionList().size(), 2);

        //Assert Exception is burrowing same book again
        try{
            lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(0).getBooks().get(0).getBookId(), LocalDate.now().minusDays(5));
        }catch (UnableToLendAlreadyBurrowed ex){
            Assertions.assertEquals(ex.getMessage(), "Unable to lend the book, already burrowed");
        }

        /*
         * Case 2-
         * Lend a book to a member at some previous date
         * Assert fine
         * Assert entry being added to fine datastore
         * */
        //Simulating book being burrowed 50 days back
        lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(0).getBooks().get(2).getBookId(), LocalDate.now().minusDays(50));
        try{
            //Previous Book exists being lend > threshold book lend period
            //Mark library card status = blocked
            //Add fine in fine datastore
            lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(1).getBooks().get(0).getBookId(), LocalDate.now().minusDays(6));
        }catch (PreviousFineExistsException ex){
            Assertions.assertEquals(ex.getMessage(), "Unable to perform lending previous fine exists");
            Assertions.assertEquals(lendingController.getFineTransaction().size(), 1);
            Assertions.assertEquals(libraryCards.get(0).getLibraryCardStatus(), LibraryCardStatus.BLOCKED);
        }
    }

    /*
     * Test over lending of a book
     * Assert exception if trying to over lend books
     * */
    @Test
    public void testBookOverLending() {
        setUpControllers();
        String libraryId = createLibraryInfra();
        Library library = libraryController.getLibrary(libraryId);

        List<String> accountMembers = createAccountMember();
        List<LibraryCard> libraryCards = accountController.getMemberLibraryCards();

        /*
         * Case 1-
         * Lend some books to a member
         * Assert exception if books lend > 5
         * */
        //Burrow 5 books
        lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(0).getBooks().get(0).getBookId(), LocalDate.now().minusDays(4));
        lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(0).getBooks().get(1).getBookId(), LocalDate.now().minusDays(4));
        lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(0).getBooks().get(2).getBookId(), LocalDate.now().minusDays(4));
        lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(1).getBooks().get(0).getBookId(), LocalDate.now().minusDays(4));
        lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(1).getBooks().get(1).getBookId(), LocalDate.now().minusDays(4));

        //Should not add more than 5 books
        try {
            lendingController.createLendingTransaction(libraryCards.get(0).getCardId(), library.getRacks().get(1).getBooks().get(2).getBookId(), LocalDate.now().minusDays(4));
        }catch (LendQuotaExceededException ex){
            Assertions.assertEquals(ex.getMessage(), "Unable to perform lending, allotted quota exceeded");
        }
    }

}
