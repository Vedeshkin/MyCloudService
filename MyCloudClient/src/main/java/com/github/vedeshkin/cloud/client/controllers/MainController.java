package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileUtil;
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

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by Vedeshkin on 1/22/2019.
 * All right reserved.
 */
public class MainController implements Initializable {

    private static final Logger logger = Logger.getLogger(MainController.class.getSimpleName());
    Path localPath = Paths.get("MyCloudStorage");
    private NetworkService networkService;

    private ObservableList<FileObject> localFileList = FXCollections.observableArrayList();
    private ObservableList<FileObject> remoteFileList = FXCollections.observableArrayList();

    @FXML
    ListView<FileObject> localFiles;
    @FXML
    ListView<FileObject>  remoteFiles;
    @FXML
    Button downloadBtn;
    @FXML
    Button uploadBtn;
    @FXML
    Button updateButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // networkService = NetworkService.getInstance();
      localFiles.setItems(localFileList);
        refreshFiles();
    }

    public void refreshFiles() {
        updateLocalFiles();
    //    updateRemoteFiles();
    }

    private void updateRemoteFiles() {
        networkService.sendRequest(new Request(Requests.GET_FILE_LIST,new Object()));

    }

    private void updateLocalFiles() {

        if (Platform.isFxApplicationThread()) {
            localFileList.clear();
           localFileList.addAll(FileUtil.getFileObjectList(localPath));


        } else {
            Platform.runLater(() -> {
                localFileList.clear();
                localFileList.addAll(FileUtil.getFileObjectList(localPath));
            });

        }
    }


    public void downloadFile() {
//        networkService.sendRequest(new Request(Requests.GET_FILE,new FileObject("file")));
    }

    public void uploadFile(ActionEvent event) {
   //     networkService.sendRequest(new Request(Requests.STORE_FILE,new FileObject("file)")));
    }
}
