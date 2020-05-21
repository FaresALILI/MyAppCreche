package com.techno.ma_creche.utils;

import java.util.Calendar;
import java.util.Date;

public class StorageUtils {

    public  String hebdodate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int mondayNo = c.get(Calendar.DAY_OF_MONTH)-c.get(Calendar.DAY_OF_WEEK)+2;
        c.set(Calendar.DAY_OF_MONTH,mondayNo);
        int d=c.get(c.MONTH)+1;
        return c.get(Calendar.DAY_OF_MONTH)+"_"+d+"_"+c.get(Calendar.YEAR);
    }

    //Retourne la date de début de semaine d'une date fournie en parametre
    public  String hebdodate(String myDate) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
       // c.setTime(new Date(myDate));
        int mondayNo = c.get(Calendar.DAY_OF_MONTH)-c.get(Calendar.DAY_OF_WEEK)+2;
        c.set(Calendar.DAY_OF_MONTH,mondayNo);
        int d=c.get(c.MONTH)+1;
        return c.get(Calendar.DAY_OF_MONTH)+"_"+d+"_"+c.get(Calendar.YEAR);
    }
}
