package bank.entities.loan;

public class MortgageLoan extends BaseLoan {
    private static final int BASE_INTEREST_RATE = 3;
    private static final double BASE_AMOUNT = 50000;
    public MortgageLoan() {
        super(BASE_INTEREST_RATE, BASE_AMOUNT);
    }
}
