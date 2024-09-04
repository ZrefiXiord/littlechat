package fr.zrefixiord.chat.controller;

import io.socket.client.Socket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChatController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField nicknameField;

    @FXML
    private Button changeNicknameButton;

    @FXML
    private Button disconnectButton;

    private Socket socket;

    @FXML
    public void initialize() {
        sendButton.setOnAction(event -> sendMessage());
        changeNicknameButton.setOnAction(event -> changeNickname());
        disconnectButton.setOnAction(event -> disconnectClient());
    }

    private void disconnectClient() {
        socket.disconnect();
        switchToMainView();
    }

    private void switchToMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/zrefixiord/chat/main-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) disconnectButton.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSocket(Socket socket){
        this.socket = socket;
        socket.on("message", args -> {
            String message =(String) args[0];
            chatArea.appendText(args[1] + ": " + message + "\n");

        });

        socket.on("disconnection", args -> {
           String disconnectClient = (String) args[0];
           chatArea.appendText(disconnectClient + " disconnected.\n");
        });

        socket.on("join", args -> chatArea.appendText( "Someone joined.\n"));
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            socket.emit("message", message);
            chatArea.appendText("you: "+ message + "\n");
            messageField.clear();
        }
    }

    private void changeNickname() {
        String message = nicknameField.getText();
        if (!message.isEmpty()) {
            socket.emit("nickname", message);
            nicknameField.clear();
        }
    }
}