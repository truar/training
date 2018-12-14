package com.zenika.zenikatas.bootcamp.tax;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class StatesTaxCalculator implements TaxCalculator {

    private static Map<String, Double> taxesByCountry = new HashMap<>();

    static {
        taxesByCountry.put("CA", 1.0825);
        taxesByCountry.put("TX", 1.0625);
        taxesByCountry.put("NV", 1.08);
        taxesByCountry.put("UT", 1.0685);
        taxesByCountry.put("AL", 1.04);
    }

    @Override
    public BigDecimal calculateTax(BigDecimal price, String country) {
        return price.multiply(new BigDecimal(taxesByCountry.get(country)));
    }
}
