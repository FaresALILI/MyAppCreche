package com.techno.ma_creche.dao;

public class MyActivite {

    String dateActivity;
    String description;

    public MyActivite(String dateActivity, String description) {
        this.dateActivity = dateActivity;
        this.description = description;
    }

    public MyActivite() {
    }

    public void setDateActivity(String dateActivity) {
        this.dateActivity = dateActivity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateActivity() {
        return dateActivity;
    }

    public String getDescription() {
        return description;
    }


}
