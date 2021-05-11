package history;

public class Config {
    private static String login;
    private static final String path = "history/";

    public static void setLogin(String newLogin){
        login = newLogin;
    }

    protected static String getLogin(){
        return login;
    }

    protected static String getPath(){
        return path;
    }
}
