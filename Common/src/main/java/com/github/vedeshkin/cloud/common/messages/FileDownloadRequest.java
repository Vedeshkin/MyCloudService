package com.github.vedeshkin.cloud.common.messages;

public class FileDownloadRequest extends AbstractMessage {
    private String fileName;

    public FileDownloadRequest(String name) {
        super(MessageType.FILE_REQUEST);
        this.fileName = name;
    }

    public String getFileName() {
        return fileName;
    }
}
