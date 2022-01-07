import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Buttons implements ActionListener {
    JButton solution_button;
    JButton submit_button;
    JButton reset_button;
    Sudoku sudoku = new Sudoku();
    public Buttons() {
        solution_button = new JButton("View Solution");
        solution_button.setFocusable(false);
        solution_button.addActionListener(this::actionPerformed);
        submit_button = new JButton("Submit");
        submit_button.setFocusable(false);
        submit_button.addActionListener(this::actionPerformed);
        reset_button = new JButton("Reset");
        reset_button.setFocusable(false);
        reset_button.addActionListener(this::actionPerformed);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit_button) {
            String [][] str_board = Utils.convertToString();
            int [][] board = Utils.convertBoard(str_board);
            for(int i=0;i<board.length;i++) {
                for(int j=0;j<board[0].length;j++) {
                    if(board[i][j] < 1 || board[i][j] > 9) {
                        String message = "<html>Ensure that all the cells are filled<br>and should be integer in the range 1-9</html>";
                        Utils.setStatus(message, false);
                        JOptionPane.showMessageDialog(null,message, "Something is not Right!!", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }

            if (sudoku.VerifySolution(board) == true) {
                Utils.setStatus("Accepted: Well Played", true);
                JOptionPane.showMessageDialog(null,"Accepted: Well Played", "Result: Correct", JOptionPane.PLAIN_MESSAGE);
            } else {
                Utils.setStatus("Well tried, but sorry your solution is incorrect", false);
                JOptionPane.showMessageDialog(null,"Well tried, but sorry your solution is incorrect", "Result: Wrong", JOptionPane.PLAIN_MESSAGE);
            }
            int answer = JOptionPane.showConfirmDialog(null, "Would you like to want to play again?", "Thanks for playing", JOptionPane.YES_NO_OPTION);
            if(answer == 0) {
                resetBoard();
            } else {
                Board.frame.dispose();
            }
        } else if(e.getSource() == solution_button) {
            String [][] str_board = Utils.convertToString();
            int [][] board = Utils.convertBoard(str_board);
            if(!sudoku.VerifySolution(board)) {
                board = Utils.convertBoard(Utils.board);
            }
            sudoku.solveSudoku(board);
        } else if(e.getSource() == reset_button) {
            resetBoard();
        }
    }
    public void resetBoard() {
        String [][] board = Utils.board;
        for(int i = 0; i< Utils.M; i++) {
            for(int j = 0; j< Utils.N; j++) {
                String val = board[i][j];
                Color color = Color.WHITE;
                if(val.equals(".")) {
                    val = "";
                } else {
                    color = Color.CYAN;
                }
                Board.input_fields.get(i).get(j).setBackground(color);
                Board.input_fields.get(i).get(j).setText(val);
            }
        }
    }
}
