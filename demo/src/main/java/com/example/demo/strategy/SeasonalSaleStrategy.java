package com.example.demo.strategy;

public class SeasonalSaleStrategy implements DiscountStrategy {

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.8;
    }

}
