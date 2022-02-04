package org.fungover.storm.fileHandler.re;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePath {
    private static FilePath instance;
    private Path path;

    private FilePath() {
    }

    public static FilePath getInstance(){
        if(instance == null)
            instance = new FilePath();
        return instance;
    }

    public FilePath setPath(String path){
        this.path = Paths.get(path);
        return this;
    }

    public Path retreive(){
        return this.path;
    }
}
