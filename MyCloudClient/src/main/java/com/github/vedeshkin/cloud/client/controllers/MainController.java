package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.request.AbstractRequest;
import com.github.vedeshkin.cloud.common.FileObject;
import com.github.vedeshkin.cloud.common.FileUtil;
import com.github.vedeshkin.cloud.common.request.FileListRequest;
import com.github.vedeshkin.cloud.common.request.RequestType;
import com.github.vedeshkin.cloud.common.response.AbstractResponse;
import com.github.vedeshkin.cloud.common.response.FileListResponse;
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
    private Path localPath = Paths.get("MyLocalStorage");
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
        networkService = NetworkService.getInstance();
        localFiles.setItems(localFileList);
        remoteFiles.setItems(remoteFileList);
        Thread t = new Thread(this::readAndParseResponse);
        t.setDaemon(true);
        t.start();
        refreshFiles();
    }

    public void refreshFiles() {
        updateLocalFiles();
       updateRemoteFiles();
    }

    private void updateRemoteFiles() {
        networkService.sendRequest(new FileListRequest());

    }

    private void updateLocalFiles() {

        Platform.runLater(() -> {
                localFileList.clear();
                localFileList.addAll(FileUtil.getFileObjectList(localPath));
            });


    }


    public void downloadFile() {
       //networkService.sendRequest(new  );
    }

    public void uploadFile(ActionEvent event) {
   //     networkService.sendRequest(new AbstractRequest(RequestType.STORE_FILE,new FileObject("file)")));
    }
    private void readAndParseResponse(){
        while(true)
        {
            AbstractResponse response = networkService.readResponse();
            if (response == null) logger.info("Empty response");
            switch (response.getType()){
                case FILE_LIST:
                    logger.info("Got FileList from Server ");
                    FileListResponse fileListResponse = (FileListResponse)response;
                    Platform.runLater(() -> {
                        remoteFileList.clear();
                        remoteFileList.addAll(fileListResponse.getFileList());//looks pretty weird ,huh?
                    });

                    break;
                case FILE:
                    logger.info("Got File from Server");
                    break;
                    default:
                        logger.warning("Response not found");
            }

        }

    }
}
