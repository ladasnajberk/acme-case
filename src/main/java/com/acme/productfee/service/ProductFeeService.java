package com.acme.productfee.service;

import com.acme.productfee.client.ParserResultList;
import com.acme.productfee.model.FeeModel;
import com.acme.productfee.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ProductFeeService {

    private static final String CATEGORY = "Category";
    private static final String LIM_TOP = "Lim_Top";
    private static final String LIM_LOW = "Lim_low";
    private static final String FEE = "Fee";

    private static final Long UNLIMITED_NUMBER = 999999L;
    private static final String UNLIMITED_STRING = "unlimited";

    private FeeRepository feeRepository;

    @Autowired
    public ProductFeeService(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public List<FeeModel> convertAndSaveFees(ParserResultList rawFees) {
        List<FeeModel> fees = getFeeModels(rawFees);
        return saveFeeModels(fees);
    }

    public List<FeeModel> saveFeeModels(List<FeeModel> fees) {
        for (FeeModel feeModel : fees) {
            feeRepository.save(feeModel);
        }
        return fees;
    }

    public List<FeeModel> getFeeModels(ParserResultList rawFees) {
        List<FeeModel> fees = new ArrayList<>();
        for (Map<String, String> item : rawFees) {
            String categoryName = item.get(CATEGORY);
            String limitTop = item.get(LIM_TOP);
            String limitLow = item.get(LIM_LOW);
            String fee = item.get(FEE);

            FeeModel fm = new FeeModel();
            fm.setCategoryName(categoryName);
            if (UNLIMITED_STRING.equals(limitTop)) {
                fm.setLimitTop(UNLIMITED_NUMBER);
            } else {
                BigDecimal limitTopBD = new BigDecimal(limitTop);
                fm.setLimitTop(limitTopBD.longValue());
            }
            BigDecimal limitLowBD = new BigDecimal(limitLow);
            fm.setLimiLow(limitLowBD.longValue());
            BigDecimal feeValue = new BigDecimal(fee);

            fm.setFee(feeValue);
            fees.add(fm);
        }
        return fees;
    }
}
