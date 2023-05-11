import com.sun.deploy.util.SyncAccess;

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
        System.out.println("Created new member:" +(representationLetter.equals("P") ? "Printed" : "Handwritten")+"[id:"+listOfBooks.size()+"]");
    }

    public static void addNewMembers(String representationLetter)
    {
        if (representationLetter.equals("S"))
        {
            libraryMembers.add(new Student());
        } else if (representationLetter.equals("A")) {
            libraryMembers.add(new Academician());
        }

        System.out.println("Created new member:" +(representationLetter.equals("S") ? "Student" : "Academic")+"[id:"+libraryMembers.size()+"]");
    }

    public static void borrowBook(int bookId, int memberId, String date)
    {
        try {
            libraryMembers.get(memberId-1).borrowBook(listOfBooks.get(bookId-1),date);
            borrowedBooks.add(listOfBooks.get(bookId-1));
            System.out.println("The book ["+ bookId+"] was borrowed by member ["+ memberId+"] at "+date);
        } catch (YouCanNotTakeThisBook e) {
            System.out.println("You cannot borrow this book!");
        }
        catch (NoMoreBooks noMoreBooks)
        {
            System.out.println("You have exceeded the borrowing limit!");
        }

    }

    public static void extendBook(int bookId, int memberId, String currentDate)
    {
        try {
            libraryMembers.get(memberId-1).extendBookDeadline(listOfBooks.get(bookId-1),currentDate);
            System.out.println("The book ["+ bookId+"] was extended by member ["+ memberId+"] at "+currentDate);
            System.out.println("New deadline of book ["+bookId+"] is "+ listOfBooks.get(bookId-1).deadline);
        }
        catch (NotExtendable notExtendable)
        {
            System.out.println("You cannot extend the deadline!");
        }


    }

    public static void readBook(int bookId, int memberId, String currentDate)
    {
        //listOfBooks.get(bookId-1).read();
        if (borrowedBooks.contains(listOfBooks.get(bookId-1)))
        {
            System.out.println("You can not read this book!");
            return;
        }
        libraryMembers.get(memberId-1).readTheBook(listOfBooks.get(bookId-1),currentDate);
        booksThatAreBeingRead.add(listOfBooks.get(bookId-1));
        System.out.println("The book ["+ bookId+"] was read by member ["+ memberId+"] at "+currentDate);

    }

    public static void returnBook(int bookId,int memberId, String currentDate)
    {
        libraryMembers.get(memberId-1).returnBook(listOfBooks.get(bookId-1),currentDate);
        borrowedBooks.remove(listOfBooks.get(bookId-1));
        booksThatAreBeingRead.remove(listOfBooks.get(bookId-1));
        System.out.println("The book ["+ bookId+"] was returned by member ["+ memberId+"] at "+currentDate+" Fee: "+  (int) libraryMembers.get(memberId-1).feeToPay);
    }

    public static void getHistory()
    {
        System.out.println("History of library:\n");
        int studentCount =0;
        int academicianCount=0;
        for (LibraryMember member: libraryMembers) {
            if (member instanceof Student)
            {
                studentCount++;
                continue;
            }
            academicianCount++;
        }
        System.out.println("Number of students: "+studentCount);
        for (LibraryMember member: libraryMembers) {
            if (member.representationLetter.equals("S"))
            {
                System.out.println("Student [id:"+(libraryMembers.indexOf(member)+1)+"]");
            }
        }
        System.out.println();
        System.out.println("Number of academics: "+academicianCount);
        for (LibraryMember member: libraryMembers) {
            if (member.representationLetter.equals("A"))
            {
                System.out.println("Academic [id:"+(libraryMembers.indexOf(member)+1)+"]");
            }
        }
        System.out.println();
        System.out.println("Number of borrowed books: "+borrowedBooks.size());
        for (Book book:borrowedBooks) {
            System.out.println("The book ["+(listOfBooks.indexOf(book)+1)+"] was borrowed by ["+(libraryMembers.indexOf(book.byWho)+1)+"] at "+book.activityTime);
        }
        System.out.println();
        System.out.println("Number of read books: "+booksThatAreBeingRead.size());
        for (Book book:booksThatAreBeingRead) {
            System.out.println("The book ["+(listOfBooks.indexOf(book)+1)+"] was read by ["+(libraryMembers.indexOf(book.byWho)+1)+"] at "+book.activityTime);
        }
    }
}
