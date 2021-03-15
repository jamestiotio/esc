
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ShootTheAccountPlus {
    private int balance = 0;
    private List<Transaction> ListOfAllTransactions = new ArrayList<Transaction>();
    private String lastDebitTime;
    private String type;

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

    // this method has a long method smell
    public void DebitTransaction(int amount) {

        if (check()) {
            // reduce the amount
            balance = balance - amount;

            // add to the transaction list
            ListOfAllTransactions.add(new Transaction("debit", amount));

            // update the last debit date
            Calendar cal = Calendar.getInstance();

            lastDebitTime = cal.get(Calendar.DATE) + "." + cal.get(Calendar.MONTH) + "."
                    + cal.get(Calendar.YEAR);
        }
    }

    // this method has a long method smell
    public void Transfer(int amount, ShootTheAccount Benf) {

        if (check()) {
            // reduce the amount
            balance = balance - amount;

            // add to the transaction list
            ListOfAllTransactions.add(new Transaction("debit", amount));

            // update the last debit date
            Calendar cal = Calendar.getInstance();

            lastDebitTime = cal.get(Calendar.DATE) + "." + cal.get(Calendar.MONTH) + "."
                    + cal.get(Calendar.YEAR);

            Benf.CreditTransaction(amount);
        }
    }

    public void sendWarning() {
        if (!check())
            System.out.println("Balance must be more than 500, please deposit");
    }
}
