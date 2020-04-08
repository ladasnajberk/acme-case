package com.acme.productfee.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ExcelParserClientTest {
    @Autowired
    ExcelParserClient excelParserClient;

    @Test
    void getFileAsByteArrayResource() {
        MultipartFile mpf = getMultipartFile("/test1.xlsx");
        ByteArrayResource br = excelParserClient.getFileAsByteArrayResource(mpf);
        assertEquals("test1.xlsx", br.getFilename());
        assertTrue(br.contentLength() > 0);
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