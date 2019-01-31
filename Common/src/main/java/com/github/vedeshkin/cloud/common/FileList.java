package com.github.vedeshkin.cloud.common;

import java.util.List;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class FileList  {
    private List<FileObject> fileList;

    public List<FileObject> getFileList() {
        return fileList;
    }

    public FileList(List<FileObject> fileList) {
        this.fileList = fileList;
    }
}
