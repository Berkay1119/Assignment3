import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LibraryMember {
    protected final int bookLimit;
    protected final int limitDay;
    protected final String representationLetter;
    protected final List<AvailableBookTypes> booksToRead;

    protected List<Book> borrowedBooks = new ArrayList<>();

    protected List<Book> booksThatAreBeingRead = new ArrayList<>();
    protected float feeToPay;

    public LibraryMember(int bookLimit,int timeLimitDay, String representationLetter, List<AvailableBookTypes> allowedBooks) {
        this.bookLimit = bookLimit;
        this.limitDay = timeLimitDay;
        this.representationLetter = representationLetter;
        booksToRead = allowedBooks;
    }

    public void borrowBook(Book book, String currentTime) throws YouCanNotTakeThisBook, NoMoreBooks {
        if (!book.isItBorrowable || feeToPay!=0)
        {
            throw new YouCanNotTakeThisBook();
        }
        if (borrowedBooks.size()>=bookLimit)
        {
            throw new NoMoreBooks();
        }
        borrowedBooks.add(book);
        book.borrow(this,LocalDate.parse(currentTime, DateTimeFormatter.ISO_LOCAL_DATE));
        book.deadline= LocalDate.parse(currentTime, DateTimeFormatter.ISO_LOCAL_DATE).plusDays(limitDay);

    }

    public void returnBook(Book book,String currentTime)
    {
        borrowedBooks.remove(book);
        if (book.returnAndCalculateFee(currentTime)!=0)
        {
            feeToPay+=book.returnAndCalculateFee(currentTime);
        }
    }

    public void extendBookDeadline(Book book, String currentTime) throws NotExtendable {
            book.extend(limitDay,this);
    }

    public void readTheBook(Book book, String currentDate) throws IOException, NotAllowedBook {
        if (borrowedBooks.contains(book))
        {
            Main.fileWriter.write("You can not read this book!\n");
            throw new NotAllowedBook();
        }
        if (!booksToRead.contains(book.bookTypes))
        {
            Main.fileWriter.write("Students can not read handwritten books!\n");
            throw new NotAllowedBook();
        }
        booksThatAreBeingRead.add(book);
        book.read(currentDate,this);
    }
}
