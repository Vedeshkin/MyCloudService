package com.github.vedeshkin.cloud.client;

import com.github.vedeshkin.cloud.common.FileInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Vedeshkin on 2/7/2019.
 * All right reserved.
 */
public class UIHelper  {

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

}
