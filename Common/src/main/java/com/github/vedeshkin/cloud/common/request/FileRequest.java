package com.github.vedeshkin.cloud.common.request;

import com.github.vedeshkin.cloud.common.FileObject;

/**
 * Created by Vedeshkin on 2/1/2019.
 * All right reserved.
 */
public class FileRequest extends AbstractRequest{
    private final FileObject fileObject;

    public FileRequest(FileObject fileObject) {
        super(RequestType.GET_FILE);
        this.fileObject = fileObject;
    }

    public FileObject getFileObject() {
        return fileObject;
    }
}
