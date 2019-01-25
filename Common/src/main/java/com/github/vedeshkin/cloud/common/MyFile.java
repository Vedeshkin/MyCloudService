package com.github.vedeshkin.cloud.common;

import java.nio.file.Path;

/**
 * Created by Vedeshkin on 1/25/2019.
 * All right reserved.
 */
public class MyFile{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyFile(String name) {
        this.name = name;
    }
}
