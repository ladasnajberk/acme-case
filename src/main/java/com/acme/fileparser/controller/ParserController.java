package com.acme.fileparser.controller;

import com.acme.fileparser.service.XLSXParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("${acme.api.v1}/parser")
public class ParserController {

    private XLSXParserService parserService;

    @Autowired
    ParserController(XLSXParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/parse")
    public List<Map<String, String>> parse(@RequestBody MultipartFile file) {
        return (parserService.parse(file));
    }

}

