package com.zenika.zenikatas.bootcamp.discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ThresholdDiscountApplyer implements DiscountApplyer {

    private static List<ThresholdDiscount> thresholdDiscounts = new ArrayList<>();

    static {
        thresholdDiscounts.add(new ThresholdDiscount(50000, 0.15));
        thresholdDiscounts.add(new ThresholdDiscount(10000, 0.1));
        thresholdDiscounts.add(new ThresholdDiscount(7000, 0.07));
        thresholdDiscounts.add(new ThresholdDiscount(5000, 0.05));
        thresholdDiscounts.add(new ThresholdDiscount(1000, 0.03));
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        BigDecimal discountedPrice = new BigDecimal(price.toString());
        for(ThresholdDiscount thresholdDiscount : thresholdDiscounts) {
            if(price.intValue() >= thresholdDiscount.threshold) {
                discountedPrice = discountedPrice.multiply(new BigDecimal(1 - thresholdDiscount.discountRate));
                break;
            }
        }
        return discountedPrice;
    }


    static class ThresholdDiscount {
        public int threshold;
        public double discountRate;

        public ThresholdDiscount(int threshold, double discountRate) {
            this.threshold = threshold;
            this.discountRate = discountRate;
        }
    }
}
