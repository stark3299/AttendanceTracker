package com.example.android.attendance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Calculate_days {
    static Date date, date1;
    public Calculate_days(){}
    static int calculate_classes(String text_date, String current_date, ArrayList<Integer> days_selected){
        int total_class = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = format.parse(text_date);
            date1 = format.parse(current_date);
           } catch (ParseException e) {
             e.printStackTrace();
           }
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(date);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(date1);

        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            return -1;
        }

        do {
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            for(int i=0;i<days_selected.size();i++){
                if (startCal.get(Calendar.DAY_OF_WEEK) == days_selected.get(i) ) {
                    ++total_class;
                }
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date
        return total_class;
    }
}
