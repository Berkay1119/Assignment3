
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Book {
    protected boolean isItBorrowable;
    protected boolean isItReadableWithoutBorrowing;

    protected boolean isItExtendable;

    protected List<LibraryMember> membersThatClaimedExtension;

    protected LocalDateTime deadline;

    public void borrow()
    {
        isItBorrowable=false;
        isItExtendable=true;
    }

    public void extend(int extensionAmountForMember) {
        deadline=deadline.plusDays(extensionAmountForMember);
    }

    public void read()
    {

    }
}
