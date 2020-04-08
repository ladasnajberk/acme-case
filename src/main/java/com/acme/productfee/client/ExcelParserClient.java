package com.acme.productfee.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ExcelParserClient {

    private RestTemplate restTemplate;

    @Autowired
    public ExcelParserClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<ParserResultList> parse(String url, MultipartFile mpf) {

        ByteArrayResource fileAsResource = getFileAsByteArrayResource(mpf);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileAsResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, ParserResultList.class);

    }


    public ByteArrayResource getFileAsByteArrayResource(MultipartFile mpf) {
        ByteArrayResource fileAsResource;
        try {
            fileAsResource = new ByteArrayResource(mpf.getBytes()) {
                @Override
                public String getFilename() {
                    return mpf.getOriginalFilename();
                }

                @Override
                public long contentLength() {
                    return mpf.getSize();
                }
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileAsResource;
    }

}
