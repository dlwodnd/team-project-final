package com.green.hoteldog.common.utils;

public class DiscountCostUtil {
    public static long getDiscountCost(long cost, String discount) {
        if(discount == null || discount.isEmpty() || discount.equals("0")){
            return cost;
        }
        int discountPer = Integer.parseInt(discount.replace("%", ""));
        return (int)cost - ((long) (int) cost * discountPer / 100);
    }
}
