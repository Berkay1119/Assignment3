
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Book {
    protected boolean isItBorrowable;

    protected List<LibraryMember> membersThatClaimedExtension=new ArrayList<>();

    protected AvailableBookTypes bookTypes;

    protected LocalDate activityTime;

    protected LocalDate deadline;

    protected LibraryMember byWho;

    public void borrow(LibraryMember libraryMember,LocalDate activityTime)
    {
        this.activityTime=activityTime;
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
        activityTime= LocalDate.parse(currentDate,DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public float returnAndCalculateFee(String currentTime)
    {
        LocalDate currentDate= LocalDate.parse(currentTime, DateTimeFormatter.ISO_LOCAL_DATE);
        if (deadline==null)
        {
            return 0;
        }
        return Math.max(Period.between(deadline, currentDate).getDays(), 0);
    }
}
