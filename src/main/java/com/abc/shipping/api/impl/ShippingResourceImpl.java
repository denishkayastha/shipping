package com.abc.shipping.api.impl;

import com.abc.shipping.api.ShippingResource;
import com.abc.shipping.entity.ZipCodeData;
import com.abc.shipping.service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * API Controller
 */
@RestController
@RequestMapping("/api")
public class ShippingResourceImpl implements ShippingResource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private  ShippingService shippingService;

    /**
     * API which take zip code data and calculate the range of zipcode data.
     * @param zipcodeData
     * @return List<ZipcodeData>.
     */
    @Override
    @PostMapping
    public ResponseEntity<List<ZipCodeData>> getShippingCode(@RequestBody List<ZipCodeData> zipCodeData) {
       return new ResponseEntity(shippingService.calculateShippingCode(zipCodeData), HttpStatus.OK);
    }
}