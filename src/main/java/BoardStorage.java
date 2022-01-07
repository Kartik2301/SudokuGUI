import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

public class BoardStorage {
    public void addBoardToFile(String board) throws IOException {
        Writer output;
        output = new BufferedWriter(new FileWriter("available_boards.txt", true));
        try {
            output.append(board + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readBoardFromFile() {
        List<String> boards = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream("available_boards.txt");
            Scanner scanner = new Scanner(inputStream);
            while(scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if(s.length() > 0) {
                    System.out.println(s.trim());
                    boards.add(s.trim());
                }
            }
            scanner.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        if(boards.size() == 0) return "";
        return boards.get(new Random().nextInt(boards.size()));
    }
}
