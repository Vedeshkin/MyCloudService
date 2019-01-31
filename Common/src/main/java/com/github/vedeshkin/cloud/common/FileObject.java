package com.github.vedeshkin.cloud.common;

import java.nio.file.Path;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class FileObject {
    private String fileName;
    private String path;

    public String getFileName() {
        return fileName;
    }

    public String getAbsolutePath() {
        return path;
    }

    public FileObject(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }

    @Override
    public String toString() {
        return fileName;
    }
}
