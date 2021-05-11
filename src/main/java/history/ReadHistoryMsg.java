package history;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadHistoryMsg {
    public static String ReadHistoryMsg(){
        try (BufferedReader historyMsg = new BufferedReader(new FileReader("history/" + Config.getLogin() + ".txt"))){
            System.out.println(historyMsg.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
