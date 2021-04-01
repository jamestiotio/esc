import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ShootTheAccountPlus {
    private int balance = 0;
    private List<Transaction> ListOfAllTransactions = new ArrayList<Transaction>();
    private String lastDebitTime;
    private String lastCreditTime;
    private String type;    // "personal" or "business"

    public ShootTheAccountPlus() {
    }

    public ShootTheAccountPlus(int balance) {
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

    public boolean check() {
        return (balance >= 500 || !type.equals("personal"));
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
        if (check()) {
            // reduce the amount
            deposit(-amount);

            // add to the transaction list
            updateTransactionList("debit", amount);

            // update the last debit date
            logLatestTransactionTime("debit");
        }
    }

    public void CreditTransaction(int amount) {
        if (check()) {
            // increase the amount
            deposit(amount);

            // add to the transaction list
            updateTransactionList("credit", amount);

            // update the last credit date
            logLatestTransactionTime("credit");
        }
    }

    public void Transfer(int amount, ShootTheAccountPlus Benf) {
        if (check()) {
            this.DebitTransaction(amount);

            Benf.CreditTransaction(amount);
        }
    }

    public void sendWarning() {
        if (!check())
            System.out.println("Balance must be more than 500, please deposit");
    }
}
