package history;

import java.io.*;

public class WriteHistoryMsg implements Closeable {
    DataOutputStream historyMsg;

    public WriteHistoryMsg() throws IOException {
        historyMsg = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(Config.getHistory(), true), 512));
    }

    public void write(String msg){
        try {
            historyMsg.writeUTF("\n" + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        historyMsg.close();
    }
}
