package com.example.demo.strategy;

public class DiscountStrategy {
    public interface DiscountStrategy {
        double calculatePrice(double originalPrice);
    }
}
