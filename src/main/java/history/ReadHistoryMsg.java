package history;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadHistoryMsg {
    public static List<String> ReadHistoryMsg() {
        try (BufferedReader historyMsg = new BufferedReader(new FileReader(FileHistory.getHistory()))) {
            List<String> historyList = historyMsg.lines().collect(Collectors.toList());
            int lastMsg = historyList.size() - 1;
            return (lastMsg < 100)
                    ? historyList
                    : historyList.subList(lastMsg - 100, lastMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
