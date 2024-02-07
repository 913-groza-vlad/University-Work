import java.util.concurrent.atomic.AtomicInteger;

public class TransferOperation {
    private static AtomicInteger operationCounter = new AtomicInteger(1);
    private int operationId;
    private Account fromAccount;
    private Account toAccount;
    private double amount;

    public TransferOperation(Account fromAccount, Account toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.operationId = operationCounter.getAndIncrement();
    }

    public double getAmount() {
        return amount;
    }

    public int getTransferId() {
        return operationId;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void transfer() {
        fromAccount.transfer(toAccount, amount, this);
    }

}
