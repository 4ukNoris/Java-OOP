package bank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BankTests {
    private Bank bank;
    private Client client1;
    private Client client2;

    @Before
    public void setup() {
        this.bank = new Bank("Fibank", 2);
        this.client1 = new Client("Georgy");
        this.client2 = new Client("Pesho");
    }

    @Test
    public void testCreateBankCorrect() {
        this.bank = new Bank("Fibank", 2);
        Assert.assertEquals("Fibank", this.bank.getName());
        Assert.assertEquals(2, this.bank.getCapacity());
    }
    @Test(expected = NullPointerException.class)
    public void testCreateBankWithNullName() {
        this.bank = new Bank(null, 2);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateBankIncorrectCapacity() {
        this.bank = new Bank("Fibank", -1);
    }
    @Test
    public void testGetCount(){
        this.bank.addClient(client1);
        this.bank.addClient(client2);
        Assert.assertEquals(2, this.bank.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddClientInFullCapacity() {
        this.bank = new Bank("Fibank", 1);
        this.bank.addClient(client1);
        this.bank.addClient(client2);
    }
    @Test
    public void testRemoveClientCorrect(){
        this.bank.addClient(client2);
        this.bank.removeClient(client2.getName());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveClientNullClient(){
        this.bank.addClient(client2);
        this.bank.removeClient(client1.getName());
    }
    @Test
    public void testLoanWithdrawalReturnToFalse(){
        this.bank.addClient(client1);
        Assert.assertEquals(client1, this.bank.loanWithdrawal(client1.getName()));
        Assert.assertFalse(client1.isApprovedForLoan());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testLoanWithdrawalWithNullClient(){
        this.bank.addClient(client1);
        Assert.assertEquals(client1, this.bank.loanWithdrawal(client2.getName()));
    }
    @Test
    public void testStatistics(){
        this.bank.addClient(client1);
        this.bank.addClient(client2);
        String names = "The client Georgy, Pesho is at the Fibank bank!";
        Assert.assertEquals(names, this.bank.statistics());

    }
}
