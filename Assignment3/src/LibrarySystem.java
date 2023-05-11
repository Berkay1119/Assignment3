import com.sun.deploy.util.SyncAccess;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibrarySystem {
    private static List<Book> listOfBooks = new ArrayList<>();

    private static List<Book> borrowedBooks = new ArrayList<>();

    private static List<Book> booksThatAreBeingRead = new ArrayList<>();

    private static List<LibraryMember> libraryMembers = new ArrayList<>();
    public static void addNewBooks(String representationLetter) throws IOException {
        if (representationLetter.equals("P"))
        {
            listOfBooks.add(new PrintedBook());
        } else if (representationLetter.equals("H")) {
            listOfBooks.add(new HandWrittenBook());
        }
        Main.fileWriter.write("Created new book: " +(representationLetter.equals("P") ? "Printed " : "Handwritten ")+"[id: "+listOfBooks.size()+"]\n");
    }

    public static void addNewMembers(String representationLetter) throws IOException {
        if (representationLetter.equals("S"))
        {
            libraryMembers.add(new Student());
        } else if (representationLetter.equals("A")) {
            libraryMembers.add(new Academician());
        }

        Main.fileWriter.write("Created new member: " +(representationLetter.equals("S") ? "Student " : "Academic ")+"[id: "+libraryMembers.size()+"]\n");
    }

    public static void borrowBook(int bookId, int memberId, String date) throws IOException {
        try {
            libraryMembers.get(memberId-1).borrowBook(listOfBooks.get(bookId-1),date);
            borrowedBooks.add(listOfBooks.get(bookId-1));
            Main.fileWriter.write("The book ["+ bookId+"] was borrowed by member ["+ memberId+"] at "+date+"\n");
        } catch (YouCanNotTakeThisBook e) {
            Main.fileWriter.write("You cannot borrow this book!\n");
        }
        catch (NoMoreBooks noMoreBooks)
        {
            Main.fileWriter.write("You have exceeded the borrowing limit!\n");
        }

    }

    public static void extendBook(int bookId, int memberId, String currentDate) throws IOException {
        try {
            libraryMembers.get(memberId-1).extendBookDeadline(listOfBooks.get(bookId-1),currentDate);
            Main.fileWriter.write("The deadline of book ["+ bookId+"] was extended by member ["+ memberId+"] at "+currentDate+"\n");
            Main.fileWriter.write("New deadline of book ["+bookId+"] is "+ listOfBooks.get(bookId-1).deadline+"\n");
        }
        catch (NotExtendable notExtendable)
        {
            Main.fileWriter.write("You cannot extend the deadline!\n");
        }


    }

    public static void readBook(int bookId, int memberId, String currentDate) throws IOException {
        //listOfBooks.get(bookId-1).read();
        if (borrowedBooks.contains(listOfBooks.get(bookId-1)))
        {
            Main.fileWriter.write("You can not read this book!\n");
            return;
        }
        try {
            libraryMembers.get(memberId-1).readTheBook(listOfBooks.get(bookId-1),currentDate);
        } catch (NotAllowedBook e) {
            return;
        }
        booksThatAreBeingRead.add(listOfBooks.get(bookId-1));
        Main.fileWriter.write("The book ["+ bookId+"] was read in library by member ["+ memberId+"] at "+currentDate+"\n");

    }

    public static void returnBook(int bookId,int memberId, String currentDate) throws IOException {
        libraryMembers.get(memberId-1).returnBook(listOfBooks.get(bookId-1),currentDate);
        borrowedBooks.remove(listOfBooks.get(bookId-1));
        booksThatAreBeingRead.remove(listOfBooks.get(bookId-1));
        Main.fileWriter.write("The book ["+ bookId+"] was returned by member ["+ memberId+"] at "+currentDate+" Fee: "+
                (int) libraryMembers.get(memberId-1).feeToPay+"\n");
    }

    public static void getHistory() throws IOException {
        Main.fileWriter.write("History of library:\n\n");
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
        Main.fileWriter.write("Number of students: "+studentCount+"\n");
        for (LibraryMember member: libraryMembers) {
            if (member.representationLetter.equals("S"))
            {
                Main.fileWriter.write("Student [id: "+(libraryMembers.indexOf(member)+1)+"]\n");
            }
        }
        Main.fileWriter.write("\n");
        Main.fileWriter.write("Number of academics: "+academicianCount+"\n");
        for (LibraryMember member: libraryMembers) {
            if (member.representationLetter.equals("A"))
            {
                Main.fileWriter.write("Academic [id: "+(libraryMembers.indexOf(member)+1)+"]\n");
            }
        }

        int printedBook=0;
        int handwrittenBook=0;
        for (Book book: listOfBooks) {
            if (book.bookTypes==AvailableBookTypes.PrintedBooks)
            {
                printedBook++;
                continue;
            }
            handwrittenBook++;
        }

        Main.fileWriter.write("\n");
        Main.fileWriter.write("Number of printed books: "+printedBook+"\n");
        for (Book book: listOfBooks) {
            if (book.bookTypes==AvailableBookTypes.PrintedBooks)
            {
                Main.fileWriter.write("Printed [id: "+(listOfBooks.indexOf(book)+1)+"]\n");
            }
        }

        Main.fileWriter.write("\n");
        Main.fileWriter.write("Number of handwritten books: "+handwrittenBook+"\n");
        for (Book book: listOfBooks) {
            if (book.bookTypes==AvailableBookTypes.HandwrittenBooks)
            {
                Main.fileWriter.write("Handwritten [id: "+(listOfBooks.indexOf(book)+1)+"]\n");
            }
        }

        Main.fileWriter.write("\n");
        Main.fileWriter.write("Number of borrowed books: "+borrowedBooks.size()+"\n");
        for (Book book:borrowedBooks) {
            Main.fileWriter.write("The book ["+(listOfBooks.indexOf(book)+1)+"] was borrowed by member ["+(libraryMembers.indexOf(book.byWho)+1)+"] at "+book.activityTime+"\n");
        }
        Main.fileWriter.write("\n");
        Main.fileWriter.write("Number of books read in library: "+booksThatAreBeingRead.size()+"\n");
        for (Book book:booksThatAreBeingRead) {
            Main.fileWriter.write("The book ["+(listOfBooks.indexOf(book)+1)+"] was read in library by member ["+(libraryMembers.indexOf(book.byWho)+1)+"] at "+book.activityTime+"\n");
        }
    }
}
