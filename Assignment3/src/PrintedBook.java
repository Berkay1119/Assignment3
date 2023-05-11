import java.time.LocalDate;

public class PrintedBook extends Book{
    private boolean hasBorrowed;
    public PrintedBook()
    {
        isItBorrowable=true;
        bookTypes=AvailableBookTypes.PrintedBooks;
    }

    @Override
    public void borrow(LibraryMember libraryMember, LocalDate localDate) {
        super.borrow(libraryMember,localDate);
        hasBorrowed=true;
    }

    @Override
    public float returnAndCalculateFee(String currentTime) {
        hasBorrowed=false;
        return super.returnAndCalculateFee(currentTime);
    }
}
