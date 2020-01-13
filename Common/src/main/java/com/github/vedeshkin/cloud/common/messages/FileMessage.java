package com.github.vedeshkin.cloud.common.messages;

import com.github.vedeshkin.cloud.common.FileObject;


/**
 * Created by Vedeshkin on 2/1/2019.
 * All right reserved.
 */
public class FileMessage extends AbstractMessage {

    private final FileObject fileObject;

    public FileMessage(FileObject fileObject) {
        super(MessageType.FILE);
        this.fileObject = fileObject;
    }

    public FileObject getFileObject() {
        return fileObject;
    }
}
