package com.github.vedeshkin.cloud.common;


import java.io.Serializable;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class FileInfo implements Serializable {

    private String fileName;
    private String path;
    private long size;

    public String getFileName() {
        return fileName;
    }

    public String getAbsolutePath() {
        return path;
    }

    public FileInfo(String fileName, String path, long size) {
        this.fileName = fileName;
        this.path = path;
        this.size = size;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
