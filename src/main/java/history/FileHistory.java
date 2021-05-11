package history;

import java.io.File;
import java.io.IOException;

public class FileHistory {
    private static final String PATH = "history/";
    private static File history;

    public static void setLogin(String login) {
        history = new File(PATH + login + ".txt");
    }

    protected static File getHistory() throws IOException {
        new File(PATH).mkdirs();
        if (!history.exists()) {
            history.createNewFile();
        }
        return history;
    }
}
