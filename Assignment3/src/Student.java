
import java.util.Collections;


public class Student extends LibraryMember{

    public Student() {
        super(2, 7,"S", Collections.singletonList(AvailableBookTypes.PrintedBooks));
    }
}
