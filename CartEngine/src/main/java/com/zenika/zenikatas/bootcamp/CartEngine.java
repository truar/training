package com.zenika.zenikatas.bootcamp;

import com.zenika.zenikatas.bootcamp.data.Article;
import com.zenika.zenikatas.bootcamp.discount.DiscountApplyer;
import com.zenika.zenikatas.bootcamp.discount.ThresholdDiscountApplyer;
import com.zenika.zenikatas.bootcamp.tax.StatesTaxCalculator;
import com.zenika.zenikatas.bootcamp.tax.TaxCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartEngine {

    private static final RoundingMode TAXED_PRICE_ROUNDING = RoundingMode.HALF_EVEN;
    private static final int SCALE = 2;

    private DiscountApplyer thresholdDiscountApplyer = new ThresholdDiscountApplyer();
    private TaxCalculator statesTaxCalculator = new StatesTaxCalculator();

    public double calculateTotalPrice(Article article, String country) {
        BigDecimal discountedPrice = thresholdDiscountApplyer.applyDiscount(new BigDecimal(article.getTotal()));
        BigDecimal taxedPrice = statesTaxCalculator.calculateTax(discountedPrice, country);
        return taxedPrice.setScale(SCALE, TAXED_PRICE_ROUNDING).doubleValue();
    }

}
