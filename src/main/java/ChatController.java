import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatController {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    @FXML
    private TextArea chatArea;
    @FXML
    private TextField inputText;

    @FXML
    private void initialize() throws IOException {
        try {
            openLoginWindow();
            Main.mainStage.setTitle(Main.mainStage.getTitle() + " (" + Config.nick + ")");
            openConnection();
            addCloseListener();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка подключения");
            alert.setHeaderText("Нет подключения к серверу");
            alert.setContentText("Не забудь включить сервер!");
            alert.showAndWait();
            e.printStackTrace();
            throw e;
        }
    }

    private void openLoginWindow() throws IOException {
        Parent root = FXMLLoader.load(ClassLoader.getSystemResource("auth.fxml"));
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setScene(new Scene(root));
        loginStage.setTitle("Вход");
        loginStage.showAndWait();
    }

    private void openConnection() throws IOException {
        socket = ServerConnection.getSocket();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                while (socket.isConnected()) {
                    String strFromServer = in.readUTF();
                    if (strFromServer.equalsIgnoreCase("/end")) {
                        chatArea.appendText("Подключение отключено");
                        break;
                    }
                    chatArea.appendText(strFromServer + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addCloseListener() {
        EventHandler<WindowEvent> onCloseRequest = Main.mainStage.getOnCloseRequest();
        Main.mainStage.setOnCloseRequest(event -> {
            closeConnection();
            if (onCloseRequest != null) {
                onCloseRequest.handle(event);
            }
        });
    }

    private void closeConnection() {
        try {
            out.writeUTF("/end");
            socket.close();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMsg() {
        if (!inputText.getText().trim().isEmpty()) {
            try {
                out.writeUTF(inputText.getText().trim());
                inputText.clear();
                inputText.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка отправки сообщения");
                alert.setHeaderText("Ошибка отправки сообщения");
                alert.setContentText("При отправке сообщения возникла ошибка: " + e.getMessage());
                alert.show();
            }
        }
    }
}