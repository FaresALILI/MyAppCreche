package com.techno.ma_creche.dao;

public class MyActivite {

    String idActivity;
    String dateActivity;
    String description;
    String typeActivity;
    boolean etat;
    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }



    public MyActivite(String dateActivity, String description,String typeActivity) {
        this.dateActivity = dateActivity;
        this.description = description;
        this.typeActivity = typeActivity;
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
