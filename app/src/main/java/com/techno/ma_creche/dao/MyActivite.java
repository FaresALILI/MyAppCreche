package com.techno.ma_creche.dao;

public class MyActivite {

    String idActivity;
    String dateActivity;
    String description;
    boolean etat;

    public MyActivite(String dateActivity, String description) {
        this.dateActivity = dateActivity;
        this.description = description;
        this.etat=false;
    }
    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
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
    public String getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(String idActivity) {
        this.idActivity = idActivity;
    }


}
