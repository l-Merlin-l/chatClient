import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AuthController {
    @FXML
    public Label msg;
    @FXML
    private TextField loginTF;
    @FXML
    private PasswordField passwordF;
    private DataInputStream in;
    private DataOutputStream out;

    public AuthController() {
    }

    @FXML
    private void initialize() throws IOException {
        Socket socket = ServerConnection.getSocket();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        (new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String strFromServer = in.readUTF();
                    if (strFromServer.startsWith("/authok")) {
                        Config.nick = strFromServer.split(" ")[1];
                        Platform.runLater(() -> {
                            Stage stage = (Stage) loginTF.getScene().getWindow();
                            stage.close();
                        });
                        break;
                    } else if (strFromServer.equals("/end")) {
                        closeConnection();
                        socket.close();
                        return;
                    } else {
                        Platform.runLater(() -> {
                            msg.setText(strFromServer);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }

    @FXML
    public void auth() throws IOException {
        if (!loginTF.getText().trim().isEmpty() && !passwordF.getText().trim().isEmpty()) {
            String authString = "/auth " + loginTF.getText() + " " + passwordF.getText();
            out.writeUTF(authString);
        } else {
            msg.setText("Введите логин и пароль");
        }
    }

    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}