
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Book {
    protected boolean isItBorrowable;

    protected List<LibraryMember> membersThatClaimedExtension=new ArrayList<>();

    protected AvailableBookTypes bookTypes;

    protected LocalDateTime activityTime;

    protected LocalDate deadline;

    protected LibraryMember byWho;

    public void borrow(LibraryMember libraryMember)
    {
        byWho=libraryMember;
    }

    public void extend(int extensionAmountForMember,LibraryMember libraryMember) throws NotExtendable {
        if (membersThatClaimedExtension.contains(libraryMember))
        {
            throw new NotExtendable();
        }
        membersThatClaimedExtension.add(libraryMember);
        deadline=deadline.plusDays(extensionAmountForMember);
    }

    public void read(String currentDate, LibraryMember libraryMember)
    {
        byWho=libraryMember;
        activityTime= LocalDateTime.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public float returnAndCalculateFee(String currentTime)
    {
        LocalDateTime currentDate= LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Duration.between(currentDate,deadline).toDays();
    }
}
