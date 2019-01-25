package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.MyFile;
import com.github.vedeshkin.cloud.common.Request;
import com.github.vedeshkin.cloud.common.Requests;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class MainController implements Initializable {

    private static final Logger logger = Logger.getLogger(MainController.class.getSimpleName());
    Path localPath = Paths.get("MyCloudStorage/storage");
    private NetworkService networkService;

    private ObservableList<MyFile> localFileList = FXCollections.observableArrayList();
    private ObservableList<MyFile> remoteFileList = FXCollections.observableArrayList();

    @FXML
    ListView<MyFile> localFiles;
    @FXML
    ListView<MyFile>  remoteFiles;
    @FXML
    Button downloadBtn;
    @FXML
    Button uploadBtn;
    @FXML
    Button updateButon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = NetworkService.getInstance();

        Thread listener = new Thread();
        listener.setDaemon(true);
        listener.start();

        refreshFiles();
    }

    public void refreshFiles() {
        updateLocalFiles();
        updateRemoteFiles();
    }

    private void updateRemoteFiles() {
        networkService.sendRequest(new Request(Requests.GET_FILE_LIST,new Object()));

    }

    private void updateLocalFiles() {
        localFileList.clear();
        if (Platform.isFxApplicationThread()) {

            try {
                localFileList.addAll(Files
                        .list(localPath)
                        .filter(Files::isRegularFile)
                        .map(Path::toString)
                        .map(MyFile::new)
                        .collect(Collectors.toList()));
                localFiles.setItems(localFileList);
            } catch (IOException ex) {
                logger.info(ex.getMessage());
            }
        } else {
            Platform.runLater(() -> {
                try {
                    localFileList.addAll(Files
                            .list(localPath)
                            .filter(Files::isRegularFile)
                            .map(Path::toString)
                            .map(MyFile::new)
                            .collect(Collectors.toList()));
                    localFiles.setItems(localFileList);
                } catch (IOException ex) {
                    logger.info(ex.getMessage());
                }
            });

        }
    }


    public void downloadFile() {
        networkService.sendRequest(new Request(Requests.GET_FILE,new MyFile("file")));
    }

    public void uploadFile(ActionEvent event) {
        networkService.sendRequest(new Request(Requests.STORE_FILE,new MyFile("file)")));
    }
}
