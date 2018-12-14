package com.zenika.zenikatas.bootcamp.tax;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum StatesTaxCalculator implements TaxCalculator {

    CA(1.0825),
    TX(1.0625),
    NV(1.08),
    UT(1.0685),
    AL(1.04);

    private static Map<String, StatesTaxCalculator> taxesByCountry = new HashMap<>();

    static {
        taxesByCountry.put("CA", CA);
        taxesByCountry.put("TX", TX);
        taxesByCountry.put("NV", NV);
        taxesByCountry.put("UT", UT);
        taxesByCountry.put("AL", AL);
    }

    private double taxRate;

    StatesTaxCalculator(double taxRate) {
        this.taxRate = taxRate;
    }

    public static StatesTaxCalculator of(String countryCode) {
        return taxesByCountry.get(countryCode);
    }

    @Override
    public BigDecimal calculateTax(BigDecimal price) {
        return price.multiply(new BigDecimal(taxRate));
    }
}
