package minesweeper;

import java.util.Random;

class Gameboard {
    private int rows;
    private int columns;
    private int mines;
    private Tile[][] matrix;
    private String difficulty;
    private boolean cheat;
    private boolean repeatedBoard;

    private Gameboard(int rows, int columns, int mines, Tile[][] matrix, String difficulty, boolean cheat, boolean repeatedBoard) {
        this.setRows(rows);
        this.setColumns(columns);
        this.setMines(mines);
        this.setMatrix(matrix);
        this.setDifficulty(difficulty);
        this.setCheat(cheat);
        this.setRepeatedBoard(repeatedBoard);
    }

    static Gameboard generateGameboard(int rows, int columns, int mines, String difficulty, int y, int x) {
        Tile[][] matrix = new Tile[rows][columns];
        // Tạo bảng game trống
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                matrix[row][col] = new Tile(row,col,0,false,false,false, false);
            }
        }
        // Đặt mìn ngẫu nhiên trong bảng
        for (int i=0; i < mines; i++) {
            int row = getRandomInt(rows);
            int col = getRandomInt(columns);
            if (!matrix[row][col].isMine() && row!=y && row!=x)     // tránh đặt 2 mìn vào cùng 1 ô
                matrix[row][col].setMine(true);                     // Đồng thời sẽ loại bỏ ô mìn nếu đó là ô đầu tiên người chơi chọn
            else i--;
        }
        // Kiểm tra trong 8 ô liền kề có ô nào là mìn không
        for (int row=0; row < rows ; row++) {
            for (int col=0;  col < columns; col++) {
                if (!matrix[row][col].isMine()) {
                    if (row>0 && col>0 && matrix[row-1][col-1].isMine()) matrix[row][col].setAdjMines(matrix[row][col].getAdjMines()+1);
                    if (col>0 && matrix[row][col-1].isMine()) matrix[row][col].setAdjMines(matrix[row][col].getAdjMines()+1);
                    if (row<rows-1 && col>0 && matrix[row+1][col-1].isMine()) matrix[row][col].setAdjMines(matrix[row][col].getAdjMines()+1);
                    if (row>0 && matrix[row-1][col].isMine()) matrix[row][col].setAdjMines(matrix[row][col].getAdjMines()+1);
                    if (row<rows-1 && matrix[row+1][col].isMine()) matrix[row][col].setAdjMines(matrix[row][col].getAdjMines()+1);
                    if (row>0 && col<columns-1 && matrix[row-1][col+1].isMine()) matrix[row][col].setAdjMines(matrix[row][col].getAdjMines()+1);
                    if (col<columns-1 && matrix[row][col+1].isMine()) matrix[row][col].setAdjMines(matrix[row][col].getAdjMines()+1);
                    if (row<rows-1 && col<columns-1 && matrix[row+1][col+1].isMine()) matrix[row][col].setAdjMines(matrix[row][col].getAdjMines()+1);
                }
            }
        }
        return new Gameboard(rows,columns,mines,matrix,difficulty,false,false);
    }

    private static int getRandomInt(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    int getColumns() {
        return columns;
    }

    private void setColumns(int columns) {
        this.columns = columns;
    }

    int getRows() {
        return rows;
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    Tile[][] getMatrix() {
        return matrix;
    }

    private void setMatrix(Tile[][] matrix) {
        this.matrix = matrix;
    }

    int getMines() {
        return mines;
    }

    private void setMines(int mines) {
        this.mines = mines;
    }

    String getDifficulty() { return difficulty; }

    private void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    boolean usedCheat() { return !cheat; }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

    boolean isRepeatedBoard() { return repeatedBoard; }

    public void setRepeatedBoard(boolean repeatedBoard) {
        this.repeatedBoard = repeatedBoard;
    }
}