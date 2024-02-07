import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BankApplication {

    public List<Account> createAccounts(int nr) {
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < nr; i++) {
            double balance = 100 + Math.random() * 10000;
            accounts.add(new Account(i + 1, balance));
        }

        return accounts;
    }

    public void start() {
        long startTime = System.currentTimeMillis();

        int numberOfThreads = 25;
        int numberOfAccounts = 10;
        List<Account> accounts = createAccounts(numberOfAccounts);
        accounts.sort(Comparator.comparing(Account::getAccountId));

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            int A = (int) (Math.random() * accounts.size());
            int B = (int) (Math.random() * accounts.size());
            while (A == B) {
                B = (int) (Math.random() * accounts.size());
            }
            Account accountA = accounts.get(A);
            Account accountB = accounts.get(B);
            double amount = 100 + Math.random() * 1000;
            Thread transferThread = new Thread(() -> {
                TransferOperation transferOperation = new TransferOperation(accountA, accountB, amount);
                transferOperation.transfer();
            });

            threads.add(transferThread);

            if (i % 10 == 0) {
                CheckerThread checkerThread = new CheckerThread(accounts);
                threads.add(checkerThread);
            }
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        CheckerThread checkerThread = new CheckerThread(accounts);
        checkerThread.start();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n\nHaving " + numberOfAccounts + " accounts and " + numberOfThreads + " threads, the program execution took " + duration + " milliseconds.");
    }
}
