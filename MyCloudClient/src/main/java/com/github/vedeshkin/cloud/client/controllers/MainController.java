package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.client.UIHelper;
import com.github.vedeshkin.cloud.client.network.NetworkService;
import com.github.vedeshkin.cloud.common.FileInfo;
import com.github.vedeshkin.cloud.common.FileUtil;
import com.github.vedeshkin.cloud.common.request.FileListRequest;
import javafx.application.Platform;
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

    public void uploadFile() {
        FileInfo fileInfo = localFiles.getSelectionModel().getSelectedItem();
        long fileSize = 0;
        try {
            fileSize = Files.size(Paths.get(fileInfo.getAbsolutePath()));
        }catch (IOException e){
            e.printStackTrace();
            //if we get exception here - then we should notify a user.
            //Seems like we don't have file here.
        }
        //TODO:
        //we should utilize  somehow there
        if (fileSize > FileUtil.MAX_MESSAGE_SIZE)
        {
            //here we should create a file cut into pieces and sent it over network.

        }





    }

}
