import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CheckerThread extends Thread {
    private List<Account> accounts;

    public CheckerThread(List<Account> accounts) {
        this.accounts = accounts;
    }


    @Override
    public void run() {

        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).getLock().lock();
        }
        System.out.println("\nConsistency check is being executed");
        for (Account account : accounts) {
            account.checkBalanceAndOperations();
        }

        for (int i = accounts.size() - 1; i >= 0; i--) {
            accounts.get(i).getLock().unlock();
        }

    }
}
