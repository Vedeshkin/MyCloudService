package com.github.vedeshkin.cloud.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //show login
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent login = fxmlLoader.load();
        stage.setTitle("Login");
        Scene scene = new Scene(login);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
