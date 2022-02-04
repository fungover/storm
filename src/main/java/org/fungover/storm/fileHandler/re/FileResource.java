package org.fungover.storm.fileHandler.re;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileResource {
    private static FileResource instance;
    private byte[] file;

    private FileResource() {

    }

    public static FileResource getInstance() {
        if (instance == null)
            instance = new FileResource();
        return instance;
    }

    public FileResource loadFile(Path path) {
        try {
            file = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public byte[] retreiveBytes(){
        return this.file;
    }
}
