package com.green.hoteldog.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDiscountInfo {
    private long roomCost;
    private String discount = "0";

    public RoomDiscountInfo setDiscountCost(long roomCost,String discount) {
        if(discount == null || discount.isEmpty() || discount.equals("0")){
            this.roomCost = roomCost;
            this.discount = discount;
            return this;
        }
        else {
            int discountPer = Integer.parseInt(discount.replace("%", ""));
            this.roomCost = (int)roomCost - ((long) (int) roomCost * discountPer / 100);
            this.discount = discount;
            return this;
        }

    }
}
