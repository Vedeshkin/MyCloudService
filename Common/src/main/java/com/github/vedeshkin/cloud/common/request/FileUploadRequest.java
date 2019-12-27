package com.github.vedeshkin.cloud.common.request;

import com.github.vedeshkin.cloud.common.FileObject;

/**
 * Created by Vedeshkin on 2/1/2019.
 * All right reserved.
 */
public class FileUploadRequest extends AbstractRequest {

    private final FileObject fileObject;

    public FileUploadRequest(FileObject fileObject) {
        super(RequestType.FILE_UPLOAD);
        this.fileObject = fileObject;
    }

    public FileObject getFileObject() {
        return fileObject;
    }
}
