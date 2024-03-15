package com.green.hoteldog.common.utils;

import com.green.hoteldog.common.entity.ReservationEntity;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    public static List<LocalDate> getDatesNextYear() {
        List<LocalDate> datesThisYear = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(today.getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(today.getYear(), 12, 31);

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            datesThisYear.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return datesThisYear;
    }

    public static List<LocalDate> getDatesThisYear() {
        List<LocalDate> datesThisYear = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(today.getYear(), 12, 31);

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            datesThisYear.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        if (startDate.getMonthValue() - endDate.getMonthValue() <= 2){
            datesThisYear.addAll(getDatesNextYear());
        }

        return datesThisYear;
    }

    public static long getRefundAmount(ReservationEntity reservationEntity, long paymentAmount) {
        Period dateDiff = Period.between( LocalDate.now(),reservationEntity.getToDate());
        if (dateDiff.getDays() >= 7) {
            return paymentAmount;
        } else if (dateDiff.getDays() > 3) {
            return paymentAmount * 80 / 100;
        } else if (dateDiff.getDays() > 1) {
            return paymentAmount * 50 / 100;
        } else {
            return 0;
        }
    }
}
