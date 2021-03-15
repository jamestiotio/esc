import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class Transaction {
    private String type;
    private int amount;

    public Transaction(String newType, int newAmount) {
        this.type = newType;
        this.amount = newAmount;
    }
}


public class AccountNoSmell {
    private int balance = 0;
    private List<Transaction> ListOfAllTransactions = new ArrayList<Transaction>();
    private String lastDebitTime;
    private String lastCreditTime;

    public AccountNoSmell() {
    }

    public AccountNoSmell(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void setBalance(int amount) {
        balance = amount;
    }

    public int getBalance() {
        return balance;
    }

    // update transaction list
    private void updateTransactionList(String type, int amount) {
        // add to the transaction list
        ListOfAllTransactions.add(new Transaction(type, amount));
    }

    // log last transaction time
    private void logLatestTransactionTime(String type) {
        Calendar cal = Calendar.getInstance();

        if (type.equals("debit"))
            lastDebitTime = cal.get(Calendar.DATE) + "." + cal.get(Calendar.MONTH) + "."
                    + cal.get(Calendar.YEAR);
        else if (type.equals("credit"))
            lastCreditTime = cal.get(Calendar.DATE) + "." + cal.get(Calendar.MONTH) + "."
                    + cal.get(Calendar.YEAR);
        else
            System.out.println("Unsupported");
    }

    public void DebitTransaction(int amount) {
        // reduce the amount
        deposit(-amount);

        // add to the transaction list
        updateTransactionList("debit", amount);

        // update the last debit date
        logLatestTransactionTime("debit");
    }

    public void CreditTransaction(int amount) {
        // increase the amount
        deposit(amount);

        // add to the transaction list
        updateTransactionList("credit", amount);

        // update the last credit date
        logLatestTransactionTime("credit");
    }
}
