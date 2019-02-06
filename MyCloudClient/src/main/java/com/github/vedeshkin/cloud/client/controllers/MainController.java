package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.client.Main;
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



    public static MainController mainController;
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
        localFiles.setItems(UIHelper.getLocalFileList());
        remoteFiles.setItems(UIHelper.getRemoteFileList());
        refreshFiles();
    }

    public void refreshFiles() {
        updateLocalFiles();
       updateRemoteFiles();
    }

    private void updateRemoteFiles() {

           networkService.send(new FileListRequest());

    }




    private void updateLocalFiles() {

        Platform.runLater(() ->
            UIHelper.getLocalFileList().setAll(FileUtil.getFileObjectList(localPath))
            );


    }


    public void downloadFile() {
       //networkService.sendRequest(new  );
    }

    public void uploadFile(ActionEvent event) {
   //     networkService.sendRequest(new AbstractRequest(RequestType.STORE_FILE,new FileObject("file)")));
    }

}
