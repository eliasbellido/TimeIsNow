package com.beyondthecode.timeisnow.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConverter {

    public static String converTime(int hour, int minute) throws ParseException{

        String alarmTime = Integer.toString(hour) + ":" + Integer.toString(minute);
        DateFormat inFormat = new SimpleDateFormat("HH:mm"); //HH for hour of the day (0 - 23)

        Date alarm;
        alarm = inFormat.parse(alarmTime);

        DateFormat outFormat  = new SimpleDateFormat("h:mm a");
        return outFormat.format(alarm).toLowerCase();
    }
}
