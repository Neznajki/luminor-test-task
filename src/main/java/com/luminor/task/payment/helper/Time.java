package com.luminor.task.payment.helper;

import java.sql.Timestamp;

public class Time {
    public static Integer diffTimeHours(Timestamp from, Timestamp to) {
        long diff = from.getTime() - to.getTime();
//            long diffSeconds = diff / 1000;
//            long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
//            long diffDays = diff / (24 * 60 * 60 * 1000);

        return (int) diffHours;
    }
}
