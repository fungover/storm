package org.fungover.storm.filehandler.re;

import java.nio.file.Path;

public record FileInfo(Path path, byte[] file) {

    public Path getPath() {
        return path;
    }

    public byte[] getFile() {
        return file;
    }
}
