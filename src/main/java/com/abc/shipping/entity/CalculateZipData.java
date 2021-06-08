package com.abc.shipping.entity;

import java.util.Objects;

/**
 * Entity used when calculate range between two zip code
 */
public class CalculateZipData {

    boolean validRange;
    ZipCodeData zipcodeData;

    public CalculateZipData() {

    }

    public void setValidRange(boolean validRange) {
        this.validRange = validRange;
    }

    public void setZipcodeData(ZipCodeData zipcodeData) {
        this.zipcodeData = zipcodeData;
    }

    public boolean getValidRange() {
        return validRange;
    }

    public ZipCodeData getZipCodeData() {
        return zipcodeData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculateZipData that = (CalculateZipData) o;
        return getValidRange() == that.getValidRange() &&
                Objects.equals(getZipCodeData(), that.getZipCodeData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValidRange(), getZipCodeData());
    }

    @Override
    public String toString() {
        return "CalculateZipData{" +
                "validRange=" + validRange +
                ", zipcodeData=" + zipcodeData +
                '}';
    }
}