package org.fungover.storm.fileHandler.re;

import java.nio.file.Path;

public record FileInfo(Path path, byte[] file) {

    public Path getPath() {
        return path;
    }

    public byte[] getFile() {
        return file;
    }
}
