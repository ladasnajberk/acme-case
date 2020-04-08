package com.acme.productfee.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(ExcelParserClient.class)
class ExcelParserClientIT {
    @Autowired
    ExcelParserClient client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void prepareServer() throws Exception {
        ParserResultList parserResultList = new ParserResultList();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("aaa", "bbb");
        parserResultList.add(hm);

        String responseString =
                objectMapper.writeValueAsString(parserResultList);

        this.server.expect(requestTo("/api/v1/parser/parse"))
                .andRespond(withSuccess(responseString, MediaType.APPLICATION_JSON));

    }


    @Test
    public void testParserEndpoint() {

        String url = "/api/v1/parser/parse";
        MultipartFile mpf = getMultipartFile("/test1.xlsx");
        ResponseEntity<ParserResultList> result = this.client.parse(url, mpf);

        ParserResultList body = result.getBody();

        assertNotNull(body);
        assertFalse(body.isEmpty(), "File should parse with at least one row");
        assertEquals("bbb", body.get(0).get("aaa"));
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
