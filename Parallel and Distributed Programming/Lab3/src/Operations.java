public class Operations {

    public static int computeElement(Matrix A, Matrix B, int row, int col) {
        int element = 0;
        for (int i = 0; i < A.getCols(); i++) {
            element += A.getElement(row, i) * B.getElement(i, col);
        }

        return element;
    }

    public static Task createRowTask(Matrix A, Matrix B, Matrix M, int index, int threadsNumber) {
        int totalElems = M.getCols() * M.getRows();
        int elemsPerTask = totalElems / threadsNumber;

        int startRow = index * elemsPerTask / M.getRows();
        int startCol = index * elemsPerTask % M.getRows();

        if (index == threadsNumber - 1) {
            elemsPerTask += totalElems % threadsNumber;
        }

        return new RowTask(startRow, startCol, elemsPerTask, A, B, M);
    }

    public static Task createColumnTask(Matrix A, Matrix B, Matrix M, int index, int threadsNumber) {
        int totalElems = M.getCols() * M.getRows();
        int elemsPerTask = totalElems / threadsNumber;

        int startRow = index * elemsPerTask % M.getRows();
        int startCol = index * elemsPerTask / M.getRows();

        if (index == threadsNumber - 1) {
            elemsPerTask += totalElems % threadsNumber;
        }

        return new ColumnTask(startRow, startCol, elemsPerTask, A, B, M);
    }

    public static Task createKthElemTask(Matrix A, Matrix B, Matrix M, int index, int threadsNumber) {
        int totalElems = M.getCols() * M.getRows();
        int elemsPerTask = totalElems / threadsNumber;

        if (index < totalElems % threadsNumber) {
            elemsPerTask++;
        }

        int startRow = index / M.getCols();
        int startCol = index % M.getCols();

        return new KthElemTask(startRow, startCol, elemsPerTask, A, B, M, threadsNumber);
    }
}
