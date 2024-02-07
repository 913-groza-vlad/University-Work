public class Matrix {
    private int rows;
    private int cols;
    private int[][] matrix;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new int[rows][cols];
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public int getElement(int row, int col) {
        return this.matrix[row][col];
    }

    public void setElement(int row, int col, int value) {
        this.matrix[row][col] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("[");
            for (int j = 0; j < cols; j++) {
                sb.append(" ").append(matrix[i][j]);
            }
            sb.append(" ]\n");
        }

        return sb.toString();
    }
}
