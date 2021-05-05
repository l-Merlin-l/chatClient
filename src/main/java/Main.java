import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("chat.fxml"));
        primaryStage.setTitle("Чат");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();

    }
}
