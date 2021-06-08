package com.abc.shipping.validator.impl;


import com.abc.shipping.entity.ZipCodeData;
import com.abc.shipping.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * ZipCode validator to validate the data is in correct format.
 */
@Component("zipcodeValidator")
public class ZipCodeValidator implements Validator<ZipCodeData> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Method to validate zipcode Data List
     * @param zipcodeDataList zipcodeDataList
     * @return boolean
     */
    @Override
    public boolean validate(final List<ZipCodeData> zipcodeDataList) {
        final Boolean[] isValid = {true};
        zipcodeDataList.stream().forEach(zipcodeData -> {
            boolean isValidObject = true;
            logger.info(" Validate zip data => "+ zipcodeData);
            if(Objects.isNull(zipcodeData) || Objects.isNull(zipcodeData.getLowerFrequency()) || Objects.isNull(zipcodeData.getHigherFrequency())) {
                isValid[0] = false;
                isValidObject = false;

            }
            if(!isValidObject || zipcodeData.getLowerFrequency() > zipcodeData.getHigherFrequency()) {
                isValid[0] = false;
            }

        });
        return isValid[0];

    }
}