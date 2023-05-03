import java.util.ArrayList;
import java.util.List;

public class LibrarySystem {
    private static List<Book> listOfBooks = new ArrayList<>();

    private static List<Book> borrowedBooks = new ArrayList<>();

    private static List<Book> booksThatAreBeingRead = new ArrayList<>();

    private static List<LibraryMember> libraryMembers = new ArrayList<>();

    public static void addNewBooks(String representationLetter)
    {
        if (representationLetter.equals("P"))
        {
            listOfBooks.add(new PrintedBook());
        } else if (representationLetter.equals("H")) {
            listOfBooks.add(new HandWrittenBook());
        }
    }

    public static void addNewMembers(String representationLetter)
    {
        if (representationLetter.equals("S"))
        {
            libraryMembers.add(new Student());
        } else if (representationLetter.equals("A")) {
            libraryMembers.add(new Academician());
        }
    }

    public static void borrowBook(int bookId, int memberId, String date)
    {
        try {
            libraryMembers.get(memberId-1).borrowBook(listOfBooks.get(bookId-1),date);
        } catch (YouCanNotTakeThisBook e) {
            System.out.println("You cannot borrow this book!");
        }
    }

    public static void extendBook(int bookId, int memberId, String currentDate)
    {
        libraryMembers.get(memberId-1).extendBookDeadline(listOfBooks.get(bookId-1),currentDate);
    }

    public static void readBook(int bookId, int memberId, String currentDate)
    {
        //listOfBooks.get(bookId-1).read();
        if (borrowedBooks.contains(listOfBooks.get(bookId-1)))
        {
            System.out.println("You can not read this book!");
        }
        libraryMembers.get(memberId-1).readTheBook(listOfBooks.get(bookId-1),currentDate);

        booksThatAreBeingRead.add(listOfBooks.get(bookId-1));
    }
}
