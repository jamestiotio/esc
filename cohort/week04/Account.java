public class Account {
    private int balance = 0;

    public Account() {
    }

    public Account(int balance) {
        this.balance = balance;
    }

    //Doubling the balance and then plus 10
    int calAmount() {
        // int ret = balance * 3 + 10;
        int ret = balance * 2;
        ret = ret + 10;
        //assert(ret == balance*2+10);
        return ret;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public boolean withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        else {
            return false;
        }
    }

    public void setBalance(int amount) {
        balance = amount;
    }

    public int getBalance() {
        return balance;
    }

    public int compare(Account yourAccount) {
        if (yourAccount.getBalance() != 0) {
            return balance / yourAccount.balance;
        }
        else {
            return -1;
        }
    }
}