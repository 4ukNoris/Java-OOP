package bank.entities.client;

public class Student extends BaseClient {
    private static final int INITIAL_INTEREST = 2;
    private static final int INCREASE_INTEREST = 1;
    public Student(String name, String ID, double income) {
        super(name, ID, INITIAL_INTEREST, income);
    }

    //TODO: Can only live in BranchBank!
    @Override
    public void increase() {
        super.setInterest(INITIAL_INTEREST + INCREASE_INTEREST);
    }
}
