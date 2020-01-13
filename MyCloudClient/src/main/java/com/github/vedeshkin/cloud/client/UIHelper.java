package com.github.vedeshkin.cloud.client;

import com.github.vedeshkin.cloud.client.controllers.LoginController;
import com.github.vedeshkin.cloud.common.FileInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.logging.Logger;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

/**
 * Created by Vedeshkin on 2/7/2019.
 * All right reserved.
 */
public class UIHelper  {

    private static Stage currentStage;
    private static Logger logger = Logger.getLogger(UIHelper.class.getName());

    public static ObservableList<FileInfo> getLocalFileList() {
        return localFileList;
    }

    public static void setLocalFileList(ObservableList<FileInfo> localFileList) {
        UIHelper.localFileList = localFileList;
    }

    public static ObservableList<FileInfo> getRemoteFileList() {
        return remoteFileList;
    }

    public static void setRemoteFileList(ObservableList<FileInfo> remoteFileList) {
        UIHelper.remoteFileList = remoteFileList;
    }

    private static ObservableList<FileInfo> localFileList = FXCollections.observableArrayList();
    private static ObservableList<FileInfo> remoteFileList = FXCollections.observableArrayList();

    public static void showInfoMsgBox(String message){
        Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        });

    }

    /*
    * Getters and setters, unusable right now.
    *
    * */

    public static Stage getCurrentStage() {
        return currentStage;
    }

    public static  void setCurrentStage(Stage stage) {
        currentStage = stage;
    }

    /*
    * Helper for changing stages.
    * TODO: refactor this.
    * @param stage
    *
    */
    public static void changeStage(String stageTitle, URL resource) {

        Stage stage = currentStage;
        Scene scene;
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle(stageTitle);
            logger.info(String.format("Stage has been changed from %s to %s ",currentStage.getTitle(),stage.getTitle()));
            currentStage = stage;
            stage.show();
        }
        catch (IOException ie ){
            logger.severe(ie.getMessage());
            ie.printStackTrace();
        }

    }
}
