package com.github.vedeshkin.cloud.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class.getName());

    @FXML
    PasswordField passwordField;


    public void handleAuth(ActionEvent event)   {
        logger.info("Button clicked");
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
            scene = new Scene((Parent) fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Main");
            stage.show();
        }
        catch (IOException ie ){
            logger.severe(ie.getMessage());
            logger.severe(ie.getStackTrace().toString());
        }

    }
}
