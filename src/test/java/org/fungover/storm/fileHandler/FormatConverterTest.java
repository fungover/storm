package org.fungover.storm.fileHandler;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


class FormatConverterTest {

    @Nested
    class FromString {
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

        @Test
        void convertingCssFileUsingMIMEShouldReturnCorrespondingMIMEType() {
            var result = FormatConverter.MIME("test.css");
            assertThat(result).isEqualTo("text/css");
        }

        @Test
        void convertingZipFileUsingMIMEShouldReturnCorrespondingMIMEType() {
            var result = FormatConverter.MIME("test.zip");
            assertThat(result).isEqualTo("application/zip");
        }

        @Test
        void convertingPdfFileUsingMIMEShouldReturnCorrespondingMIMEType() {
            var result = FormatConverter.MIME("test.pdf");
            assertThat(result).isEqualTo("application/pdf");
        }

        @Test
        void convertingFileWithComplicatedNameUsingMIMEShouldReturnCorrespondingMIMEType() {
            var result = FormatConverter.MIME("test.1.0.pdf");
            assertThat(result).isEqualTo("application/pdf");
        }
    }
    @Nested
    class FromPath {

        @Test
        void convertingPngFileUsingMIMEShouldReturnCorrespondingMIMEType() {
            Path path = Paths.get("test.pdf");

            var result = FormatConverter.MIME(path);

            assertThat(result).isEqualTo("application/pdf");
        }

        @Test
        void convertingHtmlFileUsingMIMEShouldReturnCorrespondingMIMEType() {
            Path path = Paths.get("test.html");

            var result = FormatConverter.MIME(path);

            assertThat(result).isEqualTo("text/html");
        }

    }

}