
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Book {
    protected boolean isItBorrowable;

    protected boolean isItExtendable;

    protected List<LibraryMember> membersThatClaimedExtension=new ArrayList<>();

    protected AvailableBookTypes bookTypes;

    protected LocalDateTime activityTime;

    protected LocalDateTime deadline;

    public void borrow()
    {
        isItBorrowable=false;
    }

    public void extend(int extensionAmountForMember,LibraryMember libraryMember) throws NotExtendable {
        if (membersThatClaimedExtension.contains(libraryMember))
        {
            throw new NotExtendable();
        }
        membersThatClaimedExtension.add(libraryMember);
        deadline=deadline.plusDays(extensionAmountForMember);
    }

    public void read(String currentDate)
    {
        activityTime= LocalDateTime.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public float returnAndCalculateFee(String currentTime)
    {
        LocalDateTime currentDate= LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Duration.between(currentDate,deadline).toDays();
    }
}
