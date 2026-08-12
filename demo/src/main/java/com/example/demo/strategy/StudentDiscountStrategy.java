package com.example.demo.strategy;

public class StudentDiscountStrategy implements DiscountStrategy {

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.9;
    }

}
