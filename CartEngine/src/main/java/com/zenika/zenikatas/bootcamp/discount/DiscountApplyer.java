package com.zenika.zenikatas.bootcamp.discount;

import java.math.BigDecimal;

public interface DiscountApplyer {

    BigDecimal applyDiscount(BigDecimal price);
}
