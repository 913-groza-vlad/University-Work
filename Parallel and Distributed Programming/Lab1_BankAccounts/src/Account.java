import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int accountId;
    private double balance;
    private double initialBalance;
    private Lock lock = new ReentrantLock();
    List<TransferOperation> operations;
    private static final Lock sharedLock = new ReentrantLock();

    public Account(int accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
        this.operations = new ArrayList<>();
        this.initialBalance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public List<TransferOperation> getOperations() {
        return operations;
    }

    public Lock getLock() {
        return lock;
    }

    public void transfer(Account toAccount, double amount, TransferOperation op) {
        Lock firstLock = (this.accountId < toAccount.accountId) ? this.lock : toAccount.lock;
        Lock secondLock = (this.accountId < toAccount.accountId) ? toAccount.lock : this.lock;

        firstLock.lock();
        try {
            secondLock.lock();
            try {
                if (balance >= amount) {
                    balance -= amount;
                    toAccount.balance += amount;
                    operations.add(op);
                    toAccount.operations.add(op);
                    System.out.printf("\nTransfer nr.%d of %.2f$ from Account%d to Account%d completed successfully.\nRemaining sold of Account %s is: %.2f$\n", op.getTransferId(), amount, this.getAccountId(), toAccount.getAccountId(), accountId, getBalance() );
                    // System.out.println("Remaining sold of Account" + this.getAccountId() + " is: " + this.getBalance() + "$");
                }
                else {
                    System.out.printf(String.format("\nInsufficient funds. The transaction of %.2f$ from Account%d to Account%d could not be completed.\n", amount, this.getAccountId(), toAccount.getAccountId()));
                }
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }

    public void checkBalanceAndOperations() {
        sharedLock.lock();
        try {
            double expectedBalance = initialBalance;
            System.out.println("\nLog for Account" + accountId + ":");
            for (TransferOperation op : operations) {
                if (op.getFromAccount() == this) {
                    expectedBalance -= op.getAmount();
                    System.out.printf("- Transfer nr.%d of %.2f$ to Account%d\n", op.getTransferId(), op.getAmount(), op.getToAccount().getAccountId());
                } else if (op.getToAccount() == this) {
                    expectedBalance += op.getAmount();
                    System.out.printf("+ Transfer nr.%d of %.2f$ from Account%d\n", op.getTransferId(), op.getAmount(), op.getFromAccount().getAccountId());
                }

                if (!this.getOperations().contains(op) || !op.getToAccount().getOperations().contains(op)) {
                    System.out.println("Account" + accountId + " has an operation that is missing from the source or destination account!");
                }
            }
            if (Math.abs(expectedBalance - balance) > 0.00001) {
                System.out.println("Account" + accountId + " has inconsistent balance!");
            }
            else {
                System.out.println("Account" + accountId + " balance is: " + balance + "$");
            }

        } finally {
            sharedLock.unlock();
        }
    }
}
