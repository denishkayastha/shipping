package com.abc.shipping.service;

import com.abc.shipping.entity.CalculateZipData;
import com.abc.shipping.entity.ZipCodeData;
import com.abc.shipping.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service layer to calculate the shipping zip code
 */
@Service
public class ShippingService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Validator zipcodeValidator;

    /**
     * Method to calculate shipping code based on dataList.
     * @param zipcodeDataList zipcodeDataList
     * @return List<ZipcodeData>
     */
    public List<ZipCodeData> calculateShippingCode(List<ZipCodeData> zipcodeDataList) {
        logger.info("Given list for calculation is =>{} ",zipcodeDataList);
        boolean isValidZipCodeData = zipcodeValidator.validate(zipcodeDataList);
        if(!isValidZipCodeData) {
            throw new RuntimeException("Please check and pass correct zip code data includes lower and higher zip frequency [lower frequency must be lower than higher frequency]");
        }
        List<ZipCodeData> calculatedZipDataList = new CopyOnWriteArrayList<>();
        zipcodeDataList.stream().forEach(zipcodeData -> {
            performRangeCalculations(zipcodeData, calculatedZipDataList);
        });

        logger.info("Calculated Zip code list =>{}",calculatedZipDataList);
        return calculatedZipDataList;
    }


    /**
     * Method to calculate range between to zipcode Data
     * @param zipcodeData zipcodeData
     * @param calculatedZipDataList calculatedZipDataList
     */
    private void performRangeCalculations(ZipCodeData zipcodeData, List<ZipCodeData> calculatedZipDataList) {
        if(CollectionUtils.isEmpty(calculatedZipDataList)) {
            calculatedZipDataList.add(zipcodeData);
            return;
        }
        logger.info("given zip code =>{}", zipcodeData);
        List<ZipCodeData> mergedZipList = new CopyOnWriteArrayList<>();
        AtomicBoolean isAdded  = new AtomicBoolean(false);
        for(ZipCodeData calculatedZipData : calculatedZipDataList) {
            CalculateZipData calculateZipData = calculateRanges(zipcodeData, calculatedZipData);
            if(calculateZipData.getValidRange()) {
                calculatedZipDataList.remove(calculatedZipData);
                if(!calculatedZipDataList.contains(calculateZipData.getZipCodeData())) {
                    isAdded.set(true);
                    calculatedZipDataList.add(calculateZipData.getZipCodeData());
                    zipcodeData = calculateZipData.getZipCodeData();
                }
            } else {
                mergedZipList.add(calculateZipData.getZipCodeData());
            }
        }

        if(!CollectionUtils.isEmpty(mergedZipList) && !isAdded.get()) {
            calculatedZipDataList.add(mergedZipList.get(0));
            mergedZipList.clear();
        }


    }
    
    private CalculateZipData calculateRanges(ZipCodeData zipcodeData, ZipCodeData calculatedZipData) {
        CalculateZipData calculate = new CalculateZipData();
        if(calculatedZipData.getHigherFrequency() >=  zipcodeData.getLowerFrequency()) {
            Integer lower = 0;
            Integer higher = 0;
            if(zipcodeData.getLowerFrequency() >= calculatedZipData.getLowerFrequency()) {
                lower = calculatedZipData.getLowerFrequency();
                if(zipcodeData.getHigherFrequency() >= calculatedZipData.getHigherFrequency()) {

                    higher = zipcodeData.getHigherFrequency();
                } else {
                    higher = calculatedZipData.getHigherFrequency();
                }

            }else {
                lower = zipcodeData.getLowerFrequency();
                if(calculatedZipData.getHigherFrequency() >= zipcodeData.getHigherFrequency()) {
                    higher = calculatedZipData.getHigherFrequency();
                }else {
                    higher = zipcodeData.getHigherFrequency();
                }
            }
            logger.info("Generated lower =>{} and higher => {}", lower,higher);
            calculate.setValidRange(true);
            calculate.setZipcodeData(new ZipCodeData(lower,higher));
        }else {
            calculate.setValidRange(false);
            calculate.setZipcodeData(zipcodeData);

        }
        return calculate;
    }

}