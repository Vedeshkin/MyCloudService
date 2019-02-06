package com.github.vedeshkin.cloud.client.controllers;

import com.github.vedeshkin.cloud.common.FileObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Vedeshkin on 2/7/2019.
 * All right reserved.
 */
public class UIHelper  {

    public static ObservableList<FileObject> getLocalFileList() {
        return localFileList;
    }

    public static void setLocalFileList(ObservableList<FileObject> localFileList) {
        UIHelper.localFileList = localFileList;
    }

    public static ObservableList<FileObject> getRemoteFileList() {
        return remoteFileList;
    }

    public static void setRemoteFileList(ObservableList<FileObject> remoteFileList) {
        UIHelper.remoteFileList = remoteFileList;
    }

    private static ObservableList<FileObject> localFileList = FXCollections.observableArrayList();
    private static ObservableList<FileObject> remoteFileList = FXCollections.observableArrayList();

}
