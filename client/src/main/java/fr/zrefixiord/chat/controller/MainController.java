package fr.zrefixiord.chat.controller;


import io.socket.client.IO;
import io.socket.client.Socket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URISyntaxException;

public class MainController {

    @FXML
    private TextField ipAddressField;
    @FXML
    private Button connectButton;

    private Socket socket;

    @FXML
    public void initialize() {
        connectButton.setOnAction(event -> connectToServer());
    }

    private void connectToServer() {
        String ipAddress = ipAddressField.getText();

        try {
            socket = IO.socket("http://" + ipAddress + ":3000");
            socket.connect();

            // Changer la vue une fois connecté
            switchToChatView();

        } catch (URISyntaxException e) {
            showErrorAlert("Invalid IP Address", "Please enter a valid IP address.");
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void switchToChatView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/zrefixiord/chat/chat-view.fxml"));
            Parent root = loader.load();

            // Récupère le contrôleur du chat et configure le socket
            ChatController chatController = loader.getController();
            chatController.setSocket(socket);

            Stage stage = (Stage) connectButton.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}