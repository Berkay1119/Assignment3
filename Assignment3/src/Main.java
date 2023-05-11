// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private  static String[] commandElements;
    public static void main(String[] args) {
        String[] lines=ReadFromFile.readFile("input.txt");
        for (String line: lines) {
            commandElements=line.split("\t");
            switch (commandElements[0])
            {
                case "addBook":
                    LibrarySystem.addNewBooks(commandElements[1]);
                    break;
                case "addMember":
                    LibrarySystem.addNewMembers(commandElements[1]);
                    break;
                case "borrowBook":
                    LibrarySystem.borrowBook(Integer.parseInt(commandElements[1]),
                            Integer.parseInt(commandElements[2]),commandElements[3]);
                    break;
                case "returnBook":
                    LibrarySystem.returnBook(Integer.parseInt(commandElements[1]),Integer.parseInt(commandElements[2]),commandElements[3]);
                    break;
                case "extendBook":
                    LibrarySystem.extendBook(Integer.parseInt(commandElements[1]),Integer.parseInt(commandElements[2]),commandElements[3]);
                    break;
                case "readInLibrary":
                    LibrarySystem.readBook(Integer.parseInt(commandElements[1]),Integer.parseInt(commandElements[2]),commandElements[3]);
                    break;
                case "getTheHistory":
                    LibrarySystem.getHistory();
                    break;
            }
        }
    }
}