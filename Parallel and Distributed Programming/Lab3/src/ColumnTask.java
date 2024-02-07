public class ColumnTask extends Task {

        public ColumnTask(int startRow, int startColumn, int count, Matrix A, Matrix B, Matrix M) {
            super(startRow, startColumn, count, A, B, M);
        }

        @Override
        public void findPositionsInMatrix() {
            int i = startRow;
            int j = startColumn;
            int elemsCount = this.count;
            while (elemsCount > 0 && i < M.getRows() && j < M.getCols()) {
                positionsOfElements.add(new Pair<>(i, j));
                elemsCount--;
                i++;
                if (i == M.getRows()) {
                    i = 0;
                    j++;
                }
            }
        }
}
