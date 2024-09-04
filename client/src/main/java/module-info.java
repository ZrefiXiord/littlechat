module fr.zrefixiord.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires socket.io.client;
    requires engine.io.client;
    requires json;


    opens fr.zrefixiord.chat to javafx.fxml;
    exports fr.zrefixiord.chat;
    exports fr.zrefixiord.chat.controller;
    opens fr.zrefixiord.chat.controller to javafx.fxml;
}