package com.github.vedeshkin.cloud.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;


/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class Client extends Application {

    private static Logger logger = Logger.getLogger(Client.class.getSimpleName());


    @Override
    public void start(Stage stage) throws Exception {
        //TODO:Seems as a duplicate of UIHelper#changeStage()
        //Perhaps it should be a simple login dialog?
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent login = fxmlLoader.load();
        stage.setTitle("Login");
        Scene scene = new Scene(login);
        stage.setScene(scene);
        UIHelper.setCurrentStage(stage);
        stage.show();
        logger.info(String.format("Application started.Current stage is :%s ", stage.getTitle()));

    }

    public static void main(String[] args) {
        launch(args);
    }

}
