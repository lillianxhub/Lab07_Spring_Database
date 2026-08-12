package com.example.demo.strategy;

import org.springframework.stereotype.Component;
import com.example.demo.strategy.DiscountStrategy;

@Component
public class DiscountContext {

    private DiscountStrategy discountStrategy;

    public DiscountStrategy getStrategy(String discountType) {
        if (discountType == null) {
            return new NoDiscountStrategy();
        }

        switch (discountType.toUpperCase()) {
            case "SEASONAL":
                return new SeasonalSaleStrategy();
            case "STUDENT":
                return new StudentDiscountStrategy();
            default:
                return new NoDiscountStrategy();
        }
    }
}
