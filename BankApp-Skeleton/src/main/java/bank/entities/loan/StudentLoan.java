package bank.entities.loan;

public class StudentLoan extends BaseLoan {
    private static final int BASE_INTEREST_RATE = 1;
    private static final double BASE_AMOUNT = 10000;
    public StudentLoan() {
        super(BASE_INTEREST_RATE, BASE_AMOUNT);
    }
}
