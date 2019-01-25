package com.github.vedeshkin.cloud.common;

import java.util.List;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class FileList  {
    private List<MyFile> fileList;

    public List<MyFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<MyFile> fileList) {
        this.fileList = fileList;
    }

    public FileList(List<MyFile> fileList) {
        this.fileList = fileList;
    }
}
