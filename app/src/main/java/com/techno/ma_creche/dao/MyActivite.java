package com.techno.ma_creche.dao;

import java.io.Serializable;
import java.util.Collection;

public class MyActivite implements Serializable {

    String idActivity;
    String dateActivity;
    String description;
    String typeActivity;
    String objet;
    boolean etat;
    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }



    //Collection<String>listFiles;
    Collection<FichierDistant>listFiles;
    public MyActivite(String dateActivity, String description,String typeActivity,String objet) {
        this.dateActivity = dateActivity;
        this.description = description;
        this.typeActivity = typeActivity;
        this.objet = objet;
        this.etat=false;
    }

    public String getObjet() { return objet; }
    public void setObjet(String objet) { this.objet = objet; }
    public boolean isEtat() {
        return etat;
    }
    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public MyActivite() { }


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

    public Collection<FichierDistant> getListFiles() {
        return listFiles;
    }

    public void setListFiles(Collection<FichierDistant> listFiles) {
        this.listFiles = listFiles;
    }

}
