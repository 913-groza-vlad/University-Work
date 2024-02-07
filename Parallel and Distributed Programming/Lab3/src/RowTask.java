public class RowTask extends Task {

    public RowTask(int startRow, int startColumn, int count, Matrix A, Matrix B, Matrix M) {
        super(startRow, startColumn, count, A, B, M);
    }

    @Override
    public void findPositionsInMatrix() {
        int i = startRow;
        int j = startColumn;
        while (count > 0 && i < M.getRows() && j < M.getCols()) {
            positionsOfElements.add(new Pair<>(i, j));
            count--;
            j++;
            if (j == M.getCols()) {
                j = 0;
                i++;
            }
        }
    }
}
