package history;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOHistoryMsg {



    public static String read(){
        try(BufferedReader historyMsg = new BufferedReader(new FileReader("history/" + Config.getLogin() + ".txt"))){

        }catch (IOException e){
            e.fillInStackTrace();
        }
        return null;
    }

    public static void write(String msg){

    }

    public static void close(){

    }
}
