package history;

import java.io.*;

public class WriteHistoryMsg implements Closeable {
    DataOutputStream historyMsg;
    File out;
    public WriteHistoryMsg() throws FileNotFoundException {
        out = new File(Config.getPath() + Config.getLogin());
        if(out.exists()){
            out.mkdirs();
        }
        historyMsg = new DataOutputStream(new FileOutputStream(out, true));

    }

    public void write(String msg){
        try {
            historyMsg.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        historyMsg.close();
    }
}
