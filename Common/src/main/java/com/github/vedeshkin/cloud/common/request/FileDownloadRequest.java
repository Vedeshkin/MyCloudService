package com.github.vedeshkin.cloud.common.request;

public class FileDownloadRequest extends AbstractRequest{
    private String fileName;

    public FileDownloadRequest(String name) {
        super(RequestType.FILE_DOWNLOAD);
        this.fileName = name;
    }

    public String getFileName() {
        return fileName;
    }
}
