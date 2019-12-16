package com.github.vedeshkin.cloud.common.response;


import com.github.vedeshkin.cloud.common.FileInfo;

import java.util.List;


/**
 * Created by Vedeshkin on 2/1/2019.
 * All right reserved.
 */
public class FileListResponse extends AbstractResponse {

    private final List<FileInfo> fileList;

    public FileListResponse(List<FileInfo> fileList) {
        super(ResponseType.FILE_LIST);
        this.fileList = fileList;
    }

    public List<FileInfo> getFileList() {
        return fileList;
    }
}
