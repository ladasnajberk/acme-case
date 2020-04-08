package com.acme.fileparser.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class XLSXParserServiceTest {

    @Autowired
    private XLSXParserService parserService;

    @Test
    void parseCorrectFile() {

        MultipartFile mpf = getMultipartFile("/test1.xlsx");

        List<Map<String, String>> result = parserService.parse(mpf);
        assertFalse(result.isEmpty(), "Parse result should not be empty");
        assertEquals("{Value=20.0, Name=AAA}", result.get(0).toString());
        assertEquals("{Value=30.0, Name=BBB}", result.get(1).toString());

    }

    @Test
    void parseNonExistingFile() {

        try {
            parserService.parse(null);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

    }

    @Test
    void parseNonExcelFile() {

        MultipartFile mpf = getMultipartFile("/test1.txt");
        try {
            parserService.parse(mpf);
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

    }

    private MultipartFile getMultipartFile(String fileNamePath) {

        URL url = this.getClass().getResource(fileNamePath);
        Path path = Paths.get(url.getPath());
        String name = fileNamePath.substring(1);
        String originalFileName = fileNamePath.substring(1);

        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return new MockMultipartFile(name,
                originalFileName, contentType, content);
    }
}
