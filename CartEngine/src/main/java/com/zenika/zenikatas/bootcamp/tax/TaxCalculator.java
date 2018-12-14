package com.zenika.zenikatas.bootcamp.tax;

import java.math.BigDecimal;

public interface TaxCalculator {

    BigDecimal calculateTax(BigDecimal price);

}
