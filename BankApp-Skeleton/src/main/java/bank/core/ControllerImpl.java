package bank.core;

import bank.common.ConstantMessages;
import bank.common.ExceptionMessages;
import bank.entities.bank.Bank;
import bank.entities.bank.BranchBank;
import bank.entities.bank.CentralBank;
import bank.entities.client.Adult;
import bank.entities.client.Client;
import bank.entities.client.Student;
import bank.entities.loan.Loan;
import bank.entities.loan.MortgageLoan;
import bank.entities.loan.StudentLoan;
import bank.repositories.LoanRepository;

import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerImpl implements Controller {
    private LoanRepository loans;
    private Map<String, Bank> banks;

    public ControllerImpl() {
        this.loans = new LoanRepository();
        this.banks = new LinkedHashMap<>();
    }

    @Override
    public String addBank(String type, String name) {
        Bank bank;
        switch (type) {
            case "BranchBank":
                bank = new BranchBank(name);
                break;
            case "CentralBank":
                bank = new CentralBank(name);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_BANK_TYPE);
        }
        this.banks.putIfAbsent(name, bank);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_BANK_OR_LOAN_TYPE, type);
    }

    @Override
    public String addLoan(String type) {
        Loan loan;
        switch (type) {
            case "StudentLoan":
                loan = new StudentLoan();
                break;
            case "MortgageLoan":
                loan = new MortgageLoan();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_LOAN_TYPE);
        }
        this.loans.addLoan(loan);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_BANK_OR_LOAN_TYPE, type);
    }

    @Override
    public String returnedLoan(String bankName, String loanType) {
        Loan returnedLoan = this.loans.findFirst(loanType);
        if (returnedLoan == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_LOAN_FOUND, loanType));
        }
        this.banks.get(bankName).addLoan(returnedLoan);
        this.loans.removeLoan(returnedLoan);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CLIENT_OR_LOAN_TO_BANK, loanType, bankName);
    }

    @Override
    public String addClient(String bankName, String clientType, String clientName, String clientID, double income) {
        Client client;
        switch (clientType) {
            case "Student":
                client = new Student(clientName, clientID, income);
                break;
            case "Adult":
                client = new Adult(clientName, clientID, income);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_CLIENT_TYPE);
        }
        Bank bank = this.banks.get(bankName);
        if ((clientType.equals("Adult") && bank.getClass().getSimpleName().equals("CentralBank")) ||
                (clientType.equals("Student") && bank.getClass().getSimpleName().equals("BranchBank"))) {
            bank.addClient(client);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CLIENT_OR_LOAN_TO_BANK, clientType, bankName);
        }
        return ConstantMessages.UNSUITABLE_BANK;
    }

    @Override
    public String finalCalculation(String bankName) {
        double sumLoanAmount = this.banks.get(bankName).getLoans().stream().mapToDouble(Loan::getAmount).sum();
        double sumClientIncome = this.banks.get(bankName).getClients().stream().mapToDouble(Client::getIncome).sum();
        double totalSum = sumLoanAmount + sumClientIncome;
        return String.format(ConstantMessages.FUNDS_BANK, bankName, totalSum);
    }

    @Override
    public String getStatistics() {
        StringBuilder outputMessage = new StringBuilder();
        for (Bank bank : this.banks.values()) {
            outputMessage.append(bank.getStatistics());
        }
        return outputMessage.toString().trim();
    }
}
