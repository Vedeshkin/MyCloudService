package com.github.vedeshkin.cloud.common.response;

import com.github.vedeshkin.cloud.common.FileObject;

public class FileDownloadResponse extends AbstractResponse {

    private FileObject fileObject;


    public FileDownloadResponse(FileObject fileObject) {
        super(ResponseType.FILE);
        this.fileObject = fileObject;
    }

    public FileObject getFileObject() {
        return fileObject;
    }
}
