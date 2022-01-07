import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.awt.*;
import java.io.IOException;

public class Utils {
    public static int M = 9;
    public static int N = 9;
    public static int MN = 81;
    public static String [][] board = {
            {"5","3",".",".","7",".",".",".","."},
            {"6",".",".","1","9","5",".",".","."},
            {".","9","8",".",".",".",".","6","."},
            {"8",".",".",".","6",".",".",".","3"},
            {"4",".",".","8",".","3",".",".","1"},
            {"7",".",".",".","2",".",".",".","6"},
            {".","6",".",".",".",".","2","8","."},
            {".",".",".","4","1","9",".",".","5"},
            {".",".",".",".","8",".",".","7","9"}
    };

    public static void setStatus(String message, boolean accepted) {
        if(accepted == true) {
            Board.status_label.setText(message);
            Board.status_label.setFont(new Font("MV Boli", Font.BOLD, 20));
            Board.status_label.setForeground(Color.BLUE);
        } else {
            Board.status_label.setText(message);
            Board.status_label.setFont(new Font("Verdana", Font.PLAIN, 18));
            Board.status_label.setForeground(Color.RED);
        }
    }

    public static int[][] convertBoard(String[][] board) {
        int [][] filled_board = new int[Utils.M][Utils.N];
        for(int i=0;i<board.length;i++) {
            for(int j=0;j<board[0].length;j++) {
                try {
                    filled_board[i][j] = Integer.parseInt(board[i][j]);
                } catch (NumberFormatException exception) {
                    filled_board[i][j] = -1;
                }
            }
        }
        return filled_board;
    }

    public static String[][] convertToString() {
        String[][] string_board = new String[Utils.M][Utils.N];
        for(int i = 0; i< Utils.M; i++) {
            for(int j = 0; j< Utils.N; j++) {
                String val = Board.input_fields.get(i).get(j).getText().toString();
                string_board[i][j] = val;
            }
        }
        return string_board;
    }

    public static void transform(String raw_board) {
        int running_index = 0;
        for(int i=0;i<M;i++) {
            for(int j=0;j<N;j++) {
                String cell = String.valueOf(raw_board.charAt(running_index));
                running_index++;
                if(cell.equals("0")) {
                    cell = ".";
                }
                board[i][j] = cell;
            }
        }
    }
    public static String readBoard() {
        String raw_board = new BoardStorage().readBoardFromFile();
        if(raw_board.length() == 0) {
            raw_board = "530070000600195000098000060800060003400803001700020006060000280000419005000080079";
        }
        return raw_board;
    }
    public static void getBoard() {
        String raw_board = "";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://sudoku-all-purpose-pro.p.rapidapi.com/sudoku?create=32&output=jpg")
                .get()
                .addHeader("x-rapidapi-host", "tsudoku-all-purpose-pro.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "t481bd0c7f5msh40b58f0bb981e0fp17dcc8jsna546b4da3981")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.code() == 200) {
                String s = response.body().string();
                int idx = s.indexOf("raw_data") + 12;
                raw_board = s.substring(idx, idx+81);
                new BoardStorage().addBoardToFile(raw_board);
            } else {
                raw_board = readBoard();
            }
        } catch (IOException e) {
            raw_board = readBoard();
            e.printStackTrace();
        } finally {
            transform(raw_board);
        }
    }
}