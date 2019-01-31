package com.github.vedeshkin.cloud.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vedeshkin on 1/31/2019.
 * All right reserved.
 */
public class FileUtil {
    public static List<FileObject> getFileObjectList(Path path) {
        List<FileObject> fileList = new ArrayList<>();
        try {
            fileList = Files.list(path)
                    .filter(p -> !Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS))
                    .map(p -> new FileObject(p.getFileName().toString(),p.toAbsolutePath().toString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {

        }

        return fileList;
    }

    public static void main(String[] args) {
        Path p = Paths.get("C:\\Axiom_is_back");

        for (FileObject f: getFileObjectList(p))
        {
            System.out.println(f.getAbsolutePath() + "--------------" + f.getFileName());
        }

    }
}

