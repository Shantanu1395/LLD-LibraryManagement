package com.example.Library.Management.services;

import com.example.Library.Management.constants.Constants;
import com.example.Library.Management.enums.BookStatus;
import com.example.Library.Management.enums.FineStatus;
import com.example.Library.Management.enums.LendingStatus;
import com.example.Library.Management.enums.LibraryCardStatus;
import com.example.Library.Management.exceptions.LendQuotaExceededException;
import com.example.Library.Management.exceptions.PreviousFineExistsException;
import com.example.Library.Management.exceptions.UnableToLendAlreadyBurrowed;
import com.example.Library.Management.models.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
public class LendingService {

    AccountService accountService;
    LibraryService libraryService;
    private final Map<String, Fine> fineMap = new HashMap<>();
    private final Map<String, LendTransaction> lendingMap = new HashMap<>();

    public LendingService(AccountService accountService, LibraryService libraryService) {
        this.accountService = accountService;
        this.libraryService = libraryService;
    }

    public LendTransaction createLending(String libraryCardId, String bookId, LocalDate dateOfBooking){
        validateLending(libraryCardId, bookId);
        LibraryCard libraryCard = accountService.getLibraryCardById(libraryCardId);
        BookItem bookItem = libraryService.getBook(bookId);
        bookItem.setBookStatus(BookStatus.BURROWED);
        String lendingID = UUID.randomUUID().toString();
        LendTransaction lendTransaction = new LendTransaction(lendingID, bookItem, libraryCard, dateOfBooking, dateOfBooking.plusDays(Constants.MAX_DAYS_TO_RETURN), LendingStatus.ACTIVE);
        libraryCard.addLendingTransactionToLibraryCard(lendTransaction);
        lendingMap.put(lendingID, lendTransaction);
        return lendTransaction;
    }

    public void validateLending(String libraryCardId, String bookId){
        LibraryCard libraryCard = accountService.getLibraryCardById(libraryCardId);
        BookItem bookItem = libraryService.getBook(bookId);

        if(bookItem.getBookStatus().equals(BookStatus.BURROWED)){
            throw new UnableToLendAlreadyBurrowed("Unable to lend the book, already burrowed");
        }

        List<LendTransaction> transactionList = accountService.getLibraryCardById(libraryCardId).getLendTransactionList();
        if(transactionList.size() > 5){
            throw new LendQuotaExceededException("Unable to lend books, quota exceeded");
        }

        int activeTransactions = 0;
        for (int i = 0, transactionListSize = transactionList.size(); i < transactionListSize; i++) {
            LendTransaction lendTransaction = transactionList.get(i);

            if (lendTransaction.getLendingStatus().equals(LendingStatus.FINE_ISSUED)) {
                throw new PreviousFineExistsException("Unable to perform lending previous fine exists");
            } else if (lendTransaction.getLendingStatus().equals(LendingStatus.ACTIVE))
                activeTransactions += 1;

            if (lendTransaction.getExpiryDate().compareTo(LocalDate.now()) < 0) {
                lendTransaction.setLendingStatus(LendingStatus.FINE_ISSUED);
                libraryCard.setLibraryCardStatus(LibraryCardStatus.BLOCKED);
                String fineId = UUID.randomUUID().toString();
                Fine fine = new Fine(fineId, lendTransaction, (int) ChronoUnit.DAYS.between(lendTransaction.getExpiryDate(), LocalDate.now()) * Constants.FINE_AMOUNT, FineStatus.UNPAID, LocalDate.now());
                fineMap.put(fineId, fine);
                throw new PreviousFineExistsException("Unable to perform lending previous fine exists");
            }
        }

        if(activeTransactions >= 5){
            throw new LendQuotaExceededException("Unable to perform lending, allotted quota exceeded");
        }

    }

    public List<Fine> getFineTransactions(){
        return fineMap.values().stream().toList();
    }

}
