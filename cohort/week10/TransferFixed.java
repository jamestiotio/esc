public class TransferFixed {
    private static final Object tieLock = new Object();

    private void transfer(Account fromAccount, Account toAccount, int amount) throws Exception {
        if (fromAccount.getBalance() < amount) {
            throw new Exception();
        } else {
            fromAccount.debit(amount);
            toAccount.credit(amount);
        }
    }

    public void transferMoney(Account fromAccount, Account toAccount, int amount) throws Exception {
        // Impose a fixed global sequence order by hashing the account IDs in which all threads will
        // acquire the locks to prevent lock-ordering deadlocks (lock the account with a smaller
        // hash value first)
        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    transfer(fromAccount, toAccount, amount);
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    transfer(fromAccount, toAccount, amount);
                }
            }
        } else {
            // This prevents deadlock for the corner case when attempting/trying to transfer from
            // and to the same account
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        transfer(fromAccount, toAccount, amount);
                    }
                }
            }
        }
    }
}
