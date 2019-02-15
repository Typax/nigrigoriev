package quoters;

public class TerminatorQuoterNew extends TerminatorQuoter implements Quoter{
    @Override
    public void sayQuote() {
        System.out.println("Новая реализация");
    }
}
