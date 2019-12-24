package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.request.AuthorizeRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import sun.nio.ch.Net;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class.getName());
    private NetworkService networkService = NetworkService.getInstance();

    @FXML
    PasswordField passwordField;


    public void handleAuth(ActionEvent event)   {
        logger.info("Button clicked");
        networkService.send(new AuthorizeRequest("test","test"));
        showMainStage(event);

    }



    private void showMainStage(ActionEvent event) {
        Stage stage;
        Scene scene;
        Node src = (Node) event.getSource();
        stage = (Stage) src.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Main");
            stage.show();
        }
        catch (IOException ie ){
            logger.severe(ie.getMessage());
            ie.printStackTrace();
        }

    }
}
