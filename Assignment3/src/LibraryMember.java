import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LibraryMember {
    protected final int bookLimit;
    protected final int limitDay;
    protected final String representationLetter;
    protected final List<AvailableBookTypes> booksToRead;

    protected List<Book> borrowedBooks = new ArrayList<>();

    protected float feeToPay;

    public LibraryMember(int bookLimit,int timeLimitDay, String representationLetter, List<AvailableBookTypes> allowedBooks) {
        this.bookLimit = bookLimit;
        this.limitDay = timeLimitDay;
        this.representationLetter = representationLetter;
        booksToRead = allowedBooks;
    }

    public void borrowBook(Book book, String currentTime) throws YouCanNotTakeThisBook {
        if (!book.isItBorrowable || feeToPay!=0)
        {
            throw new YouCanNotTakeThisBook();
        }
        borrowedBooks.add(book);
        book.borrow();
        book.deadline= LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void returnBook(Book book,String currentTime)
    {
        borrowedBooks.remove(book);
        if (book.returnAndCalculateFee(currentTime)!=0)
        {
            System.out.println("You must pay a penalty!");
            feeToPay+=book.returnAndCalculateFee(currentTime);
        }
    }

    public void extendBookDeadline(Book book, String currentTime)
    {
        try {
            book.extend(limitDay,this);
        }
        catch (NotExtendable notExtendable)
        {
            System.out.println("You cannot extend the deadline!\n");
        }
    }

    public void readTheBook(Book book, String currentDate) {
        if (!booksToRead.contains(book.bookTypes))
        {
            System.out.println("Students can not read handwritten books!");
        }
        book.read(currentDate);
    }
}
