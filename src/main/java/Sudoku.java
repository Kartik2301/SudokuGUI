import java.awt.*;
import java.util.HashSet;

public class Sudoku {
    public boolean VerifySolution(int [][] board) {
        HashSet<String> set = new HashSet<>();
        int i,j;
        for(i=0;i<board.length;i++) {
            for(j=0;j<board[0].length;j++) {
                if(board[i][j] == -1) continue;
                if(!set.add(board[i][j] + " Present in row " + i)) return false;
                if(!set.add(board[i][j] + " Present in col " + j)) return false;
                if(!set.add(board[i][j] + " Present in box " + i/3 + " " + j/3)) return false;
            }
        }
        return true;
    }
    public void solveSudoku(int [][] board) {
        solveSudokuHelper(board);
        Utils.setStatus("Press Reset to start a new game", true);
    }
    public boolean solveSudokuHelper(int [][] board) {
        int [] positions = containsUnassignedPositions(board);
        int row = positions[0];
        int col = positions[1];
        if(row == -1) return true;
        for(int num=1;num<=9;num++) {
            if(safeRow(num, board, row) && safeCol(num, board, col) && safeBox(num, board, row, col)) {
                board[row][col] = num;
                Board.input_fields.get(row).get(col).setText(Integer.toString(num));
                Board.input_fields.get(row).get(col).setBackground(Color.GREEN);
                Board.input_fields.get(row).get(col).setEditable(false);
                if(solveSudokuHelper(board)) return true;
                board[row][col] = -1;
                Board.input_fields.get(row).get(col).setText("");
            }
        }
        return false;
    }
    public int[] containsUnassignedPositions(int [][] board) {
        int [] res = {-1,-1};
        int i,j;
        for(i=0;i<board.length;i++) {
            for(j=0;j<board[0].length;j++) {
                if(board[i][j] == -1) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return res;
    }
    public boolean safeRow(int num, int [][] board, int row) {
        int j;
        for(j=0;j<board[0].length;j++) {
            if(board[row][j] == num) return false;
        }
        return true;
    }
    public boolean safeCol(int num, int [][] board, int col) {
        int i;
        for(i=0;i<board.length;i++) {
            if(board[i][col] == num) return false;
        }
        return true;
    }
    public boolean safeBox(int num, int [][] board, int row, int col) {
        int i,j;
        int begin_row = row - row%3;
        int begin_col = col - col%3;
        for(i=begin_row;i<begin_row+3;i++) {
            for(j=begin_col;j<begin_col+3;j++) {
                if(board[i][j] == num) return false;
            }
        }
        return true;
    }
}

