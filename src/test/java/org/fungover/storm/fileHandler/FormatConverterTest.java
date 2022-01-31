package org.fungover.storm.fileHandler;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FormatConverterTest {

    @Test
    void convertingPngFileUsingMIMEShouldReturnCorrespondingMIMEType() {
        var result = FormatConverter.MIME("cat.png");
        assertThat(result).isEqualTo("image/png");
    }

    @Test
    void convertingJpegFileUsingMIMEShouldReturnCorrespondingMIMEType() {
        var result = FormatConverter.MIME("cat.jpeg");
        assertThat(result).isEqualTo("image/jpeg");
    }

    @Test
    void convertingHtmlFileUsingMIMEShouldReturnCorrespondingMIMEType() {
        var result = FormatConverter.MIME("test.html");
        assertThat(result).isEqualTo("text/html");
    }

    @Test
    void convertingCsvFileUsingMIMEShouldReturnCorrespondingMIMEType() {
        var result = FormatConverter.MIME("test.csv");
        assertThat(result).isEqualTo("text/csv");
    }
}