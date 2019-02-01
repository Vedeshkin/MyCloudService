package com.github.vedeshkin.cloud.common.response;


import com.github.vedeshkin.cloud.common.FileObject;

import java.util.List;


/**
 * Created by Vedeshkin on 2/1/2019.
 * All right reserved.
 */
public class FileListResponse extends AbstractResponse {

    private final List<FileObject> fileList;

    public FileListResponse(List<FileObject> fileList) {
        super(ResponseType.FILE_LIST);
        this.fileList = fileList;
    }

    public List<FileObject> getFileList() {
        return fileList;
    }
}
