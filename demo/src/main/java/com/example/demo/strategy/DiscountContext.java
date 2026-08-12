package com.example.demo.strategy;

public class DiscountContext {
    private DiscountStrategy discountStrategy;

    public getDiscountStrategy() {
        switch (discountType) {
            case "seasonal":
                discountStrategy = new SeasonalSaleStrategy();
                break;
            case "student":
                discountStrategy = new StudentDiscountStrategy();
                break;
            case "none":
                discountStrategy = new NoDiscountStrategy();
                break;
            default:
                throw new IllegalArgumentException("Invalid discount type: " + discountType);
        }
        return discountStrategy;
    }
}
