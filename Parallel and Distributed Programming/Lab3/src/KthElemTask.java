public class KthElemTask extends Task {

        public KthElemTask(int startRow, int startColumn, int count, Matrix A, Matrix B, Matrix M, int k) {
            super(startRow, startColumn, count, A, B, M, k);
        }

        @Override
        public void findPositionsInMatrix() {
            int i = startRow;
            int j = startColumn;
            int elemsCount = count;
            while (elemsCount > 0 && i < M.getRows() && j < M.getCols()) {
                positionsOfElements.add(new Pair<>(i, j));
                elemsCount--;
                i += (j + k) / M.getCols();
                j = (j + k) % M.getCols();
            }
        }
}
