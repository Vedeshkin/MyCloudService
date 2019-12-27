package com.github.vedeshkin.cloud.common;

import java.io.Serializable;

/**
 * Created by Vedeshkin on 2/8/2019.
 * All right reserved.
 */
public class FileObject implements Serializable {
    private final String fileName;
    private final byte[] data;
    private final int fileLength;
    //checksum?

    public FileObject(String fileName, byte[] data, int fileLength) {
        this.fileName = fileName;
        this.data = data;
        this.fileLength = fileLength;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getData() {
        return data;
    }

    public int getFileLength() {
        return fileLength;
    }
}


