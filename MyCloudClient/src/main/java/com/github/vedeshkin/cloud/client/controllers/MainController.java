package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.client.UIHelper;
import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.FileInfo;
import com.github.vedeshkin.cloud.common.FileService;
import com.github.vedeshkin.cloud.common.FileUtil;
import com.github.vedeshkin.cloud.common.messages.FileDownloadRequest;
import com.github.vedeshkin.cloud.common.messages.FileListRequest;
import io.netty.channel.Channel;
import javafx.application.Platform;
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
    ListView<FileInfo> localFiles;
    @FXML
    ListView<FileInfo>  remoteFiles;
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
        mainController = this;
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
       FileInfo fi = remoteFiles.getFocusModel().getFocusedItem();
       if (fi == null) {
           logger.info("User clicked on download file but none selected");
           return;
       }
       networkService.send(new FileDownloadRequest(fi.getFileName()));
    }

    public void uploadFile() {
        FileInfo fi = localFiles.getFocusModel().getFocusedItem();
        if (fi == null) {
            logger.info("User clicked on upload file but none selected");
            return;
        }
        Path uploadFile = Paths.get(fi.getPath());
        Channel channel = NetworkService.getInstance().getChannel();
        FileService.getInstance().uploadFile(channel,uploadFile);

    }


    }

