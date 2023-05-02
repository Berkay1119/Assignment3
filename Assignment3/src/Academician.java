import java.util.Arrays;

public class Academician extends LibraryMember{
    public Academician() {
        super(4,14,"A", Arrays.asList(AvailableBookTypes.HandwrittenBooks,AvailableBookTypes.PrintedBooks));
    }

}
