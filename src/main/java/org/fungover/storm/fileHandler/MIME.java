package org.fungover.storm.fileHandler;

public record MIME(String type, String subtype) {
    @Override
    public String type() {
        return type;
    }

    @Override
    public String subtype() {
        return subtype;
    }
}
