package com.github.vedeshkin.cloud.common.request;

/**
 * Created by Vedeshkin on 2/1/2019.
 * All right reserved.
 */
public class FileListRequest extends AbstractRequest {
    public FileListRequest() {
        super(RequestType.GET_FILE_LIST);
    }
}
