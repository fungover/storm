package org.fungover.storm.filehandler.re;

import java.nio.file.Path;

public class FileInfo {
    private final Path path;
    private final byte[] file;

    public FileInfo(Path path, byte[] file) {
        this.path = path;
        this.file = file;
    }

    public Path getPath() {
        return path;
    }

    public byte[] getFile() {
        return file;
    }
}
