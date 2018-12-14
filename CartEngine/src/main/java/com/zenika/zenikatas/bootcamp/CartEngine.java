package com.zenika.zenikatas.bootcamp;

import com.zenika.zenikatas.bootcamp.data.Article;
import com.zenika.zenikatas.bootcamp.discount.DiscountApplyer;
import com.zenika.zenikatas.bootcamp.discount.ThresholdDiscountApplyer;
import com.zenika.zenikatas.bootcamp.tax.StatesTaxCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartEngine {

    private static final RoundingMode TAXED_PRICE_ROUNDING = RoundingMode.HALF_EVEN;
    private static final int SCALE = 2;

    private DiscountApplyer thresholdDiscountApplyer = new ThresholdDiscountApplyer();

    public double calculateTotalPrice(Article article, StatesTaxCalculator state) {
        BigDecimal discountedPrice = thresholdDiscountApplyer.applyDiscount(new BigDecimal(article.getTotal()));
        BigDecimal taxedPrice = state.calculateTax(discountedPrice);
        return taxedPrice.setScale(SCALE, TAXED_PRICE_ROUNDING).doubleValue();
    }
}
