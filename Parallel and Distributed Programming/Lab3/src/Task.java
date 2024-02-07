import java.util.ArrayList;
import java.util.List;

public abstract class Task extends Thread {
    protected int startRow, startColumn, count;
    protected Matrix A, B, M;
    protected int k;
    protected List<Pair<Integer, Integer>> positionsOfElements;

    public Task(int startRow, int startColumn, int count, Matrix A, Matrix B, Matrix M, int k) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.count = count;
        this.A = A;
        this.B = B;
        this.M = M;
        this.k = k;
        this.positionsOfElements = new ArrayList<>();
        findPositionsInMatrix();
    }

    public Task(int startRow, int startColumn, int count, Matrix A, Matrix B, Matrix M) {
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.count = count;
        this.A = A;
        this.B = B;
        this.M = M;
        this.positionsOfElements = new ArrayList<>();
        findPositionsInMatrix();
    }

    public abstract void findPositionsInMatrix();

    @Override
    public void run() {
        for (Pair<Integer, Integer> position : positionsOfElements) {
            int row = position.getFirst();
            int col = position.getSecond();
            M.setElement(row, col, Operations.computeElement(A, B, row, col));
        }
    }

}
