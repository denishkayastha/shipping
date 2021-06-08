package com.abc.shipping.validator.impl;

import com.abc.shipping.entity.ZipCodeData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ZipCodeValidatorTest {

    @Autowired
    ZipCodeValidator zipCodeValidator;

    @Test
    public void positiveValidateZipCodeData() {
        List<ZipCodeData> zipcodeDataList = Arrays.asList(new ZipCodeData(94133, 94133),
                new ZipCodeData(94200, 94299),
                new ZipCodeData(94300, 94399));
        boolean isValid = zipCodeValidator.validate(zipcodeDataList);
        assertNotNull(isValid);
        assertEquals(true, isValid);
    }

    @Test
    public void negativeValidateZipCodeDataWithInvalidData() {
        List<ZipCodeData> zipcodeDataList = Arrays.asList(new ZipCodeData(null , null),
                new ZipCodeData(94200, 94299),
                new ZipCodeData(94300, 94399));
        boolean isValid = zipCodeValidator.validate(zipcodeDataList);
        assertNotNull(isValid);
        assertEquals(false, isValid);
    }

    @Test
    public void negativeValidateZipCodeDataWithInvalidLowerAndHigherFreq() {
        List<ZipCodeData> zipCodeDataList = Arrays.asList(new ZipCodeData(123 , 22),
                new ZipCodeData(94200, 94299),
                new ZipCodeData(94300, 94399));
        boolean isValid = zipCodeValidator.validate(zipCodeDataList);
        assertNotNull(isValid);
        assertEquals(false, isValid);
    }
}
