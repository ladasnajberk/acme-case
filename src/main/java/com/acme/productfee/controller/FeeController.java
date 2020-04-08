package com.acme.productfee.controller;

import com.acme.productfee.client.ExcelParserClient;
import com.acme.productfee.client.ParserResultList;
import com.acme.productfee.model.FeeModel;
import com.acme.productfee.repository.FeeRepository;
import com.acme.productfee.service.ProductFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("${acme.api.v1}/productfee/fee")
public class FeeController {

    @Value("${acme.fileparser.serviceendpoint.url}")
    private String fileParserurl;

    private FeeRepository feeRespository;

    private ProductFeeService productFeeService;

    private ExcelParserClient excelParserClient;

    @Autowired
    public FeeController(FeeRepository feeRespository, ProductFeeService productFeeService, ExcelParserClient excelParserClient) {
        this.feeRespository = feeRespository;
        this.productFeeService = productFeeService;
        this.excelParserClient = excelParserClient;
    }

    @GetMapping("/all")
    public Iterable<FeeModel> all() {
        return feeRespository.findAll();
    }

    @GetMapping("/{id}")
    public FeeModel feeById(@PathVariable Long id) {
        return feeRespository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public FeeModel save(@RequestBody FeeModel user) {
        return feeRespository.save(user);
    }

    @PostMapping("/upload")
    public List<FeeModel> upload(@RequestBody MultipartFile file) {

        ResponseEntity<ParserResultList> clientResponse = excelParserClient.parse(fileParserurl, file);

        List<FeeModel> fees = new ArrayList<>();
        if (clientResponse.getStatusCode().is2xxSuccessful()) {
            fees = productFeeService.convertAndSaveFees(clientResponse.getBody());
        }
        return fees;

    }

}
