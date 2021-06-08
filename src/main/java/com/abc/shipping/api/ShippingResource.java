package com.abc.shipping.api;

import com.abc.shipping.entity.ZipCodeData;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShippingResource {

    ResponseEntity<List<ZipCodeData>> getShippingCode(List<ZipCodeData> zipcodeData);
}