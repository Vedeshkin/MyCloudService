package com.github.vedeshkin.cloud.common.messages;


import com.github.vedeshkin.cloud.common.FileInfo;

import java.util.List;


/**
 * Created by Vedeshkin on 2/1/2019.
 * All right reserved.
 */
public class FileListResponse extends AbstractMessage {

    private final List<FileInfo> fileList;

    public FileListResponse(List<FileInfo> fileList) {
        super(MessageType.FILE_LIST);
        this.fileList = fileList;
    }

    public List<FileInfo> getFileList() {
        return fileList;
    }
}
