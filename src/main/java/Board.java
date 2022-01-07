import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.lang.invoke.ConstantBootstraps;
import java.sql.Time;
import java.util.ArrayList;

public class Board {
    static ArrayList<ArrayList<JTextField>> input_fields;
    static JLabel status_label;
    static JFrame frame;
    public static void main(String[] args) {
        // Initialise the board
        frame = new JFrame();
        frame.setTitle("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(600,600);
        frame.setLayout(new BorderLayout());

        // Top panel to hold the status
        JPanel panel_top = new JPanel();
        status_label = new JLabel();
        status_label.setText("Welcome to the sudoku Game");
        status_label.setFont(new Font("MV Boli", Font.BOLD, 20));
        panel_top.add(status_label);

        // Central Panel to hold onto the Sudoku board
        JPanel panel_center = new JPanel();
        panel_center.setLayout(new GridLayout(9,9,1,1));

        // Create the board
        Utils.getBoard();

        input_fields = reset_board();

        for(int i = 0; i< Utils.M; i++) {
            for(int j = 0; j< Utils.N; j++) {
                panel_center.add(input_fields.get(i).get(j));
            }
        }

        for(int i = 0; i< Utils.M; i++) {
            for(int j = 0; j< Utils.N; j++) {
                final int row = i;
                final int col = j;
                input_fields.get(i).get(j).getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        input_fields.get(row).get(col).setBackground(Color.YELLOW);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        input_fields.get(row).get(col).setBackground(Color.WHITE);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {

                    }
                });
            }
        }

        // Bottom panel to hold the buttons
        JPanel panel_bottom = new JPanel();

        Buttons buttons = new Buttons();
        panel_bottom.add(buttons.submit_button);
        panel_bottom.add(buttons.solution_button);
        panel_bottom.add(buttons.reset_button);

        // Add the panels to the frame
        frame.add(panel_top, BorderLayout.NORTH);
        frame.add(panel_center, BorderLayout.CENTER);
        frame.add(panel_bottom, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    public static ArrayList<ArrayList<JTextField>> reset_board() {
        ArrayList<ArrayList<JTextField>> input_fields = new ArrayList<>();
        String [][] board = Utils.board;

        for(int i = 0; i< Utils.M; i++) {
            ArrayList<JTextField> temp = new ArrayList<>(Utils.N);
            for(int j = 0; j< Utils.N; j++) {
                JTextField textField = new JTextField();
                String val = board[i][j];
                Color color = Color.WHITE;
                if(val.equals(".")) {
                    val = "";
                } else {
                    textField.setEditable(false);
                    color = Color.CYAN;
                }
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setText(val);
                textField.setBackground(color);
                temp.add(textField);
            }
            input_fields.add(temp);
        }
        return input_fields;
    }
}
