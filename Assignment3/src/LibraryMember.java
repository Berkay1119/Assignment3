import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LibraryMember {
    protected final int bookLimit;
    protected final int limitDay;
    protected final String representationLetter;
    protected final List<AvailableBookTypes> booksToGet;

    protected List<Book> borrowedBooks = new ArrayList<>();

    public LibraryMember(int bookLimit,int timeLimitDay, String representationLetter, List<AvailableBookTypes> allowedBooks) {
        this.bookLimit = bookLimit;
        this.limitDay = timeLimitDay;
        this.representationLetter = representationLetter;
        booksToGet = allowedBooks;
    }

    public void borrowBook(Book book, String currentTime) {
        borrowedBooks.add(book);
        book.deadline= LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void returnBook(Book book,String currentTime)
    {
        borrowedBooks.remove(book);
        book.borrow();
    }

    public void extendBookDeadline(Book book, String currentTime)
    {
        book.extend(limitDay);
    }
}
