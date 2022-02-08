package org.fungover.storm.filehandler;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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

        @Test
        void convertingFileWithUnknownExtensionShouldReturnDefaultMIMEType() {
            var result = FormatConverter.MIME("fil.test");
            assertThat(result).isEqualTo("application/octet-stream");
        }

        @Test
        void convertingJpegAndJpgFileShouldReturnTheSameSameMIMEType() {
            var result = new ArrayList<String>();

            result.add(FormatConverter.MIME("test.jpeg"));
            result.add(FormatConverter.MIME("test.jpg"));

            assertThat(result).allMatch(mimeType -> mimeType.equals("image/jpeg"));
        }

        @Test
        void convertingHtmlAndHtmFileShouldReturnTheSameSameMIMEType() {
            var result = new ArrayList<String>();

            result.add(FormatConverter.MIME("test.html"));
            result.add(FormatConverter.MIME("test.htm"));

            assertThat(result).allMatch(mimeType -> mimeType.equals("text/html"));
        }

        @Test
        void convertingTiffAndTifFileShouldReturnTheSameSameMIMEType() {
            var result = new ArrayList<String>();

            result.add(FormatConverter.MIME("test.tiff"));
            result.add(FormatConverter.MIME("test.tif"));

            assertThat(result).allMatch(mimeType -> mimeType.equals("image/tiff"));
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
