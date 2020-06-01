package com.techno.ma_creche.dao;

import java.io.Serializable;

public class FichierDistant implements Serializable {
    String link;
    String nomFile;
    String typeActivity;
    String extFile;

    public String getNomFile() {
        return nomFile;
    }

    public void setNomFile(String nomFile) {
        this.nomFile = nomFile;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }

    public void setExtFile(String extFile) {
        this.extFile = extFile;
    }

    public String getLink() {
        return link;
    }



    public String getTypeActivity() {
        return typeActivity;
    }
    public String getExtFile() {
        return extFile;
    }
    public FichierDistant(String link, String nomFile, String typeActivity, String extFile) {
        this.link = link;
        this.nomFile = nomFile;
        this.typeActivity = typeActivity;
        this.extFile = extFile;
    }

    public FichierDistant() {
    }
}
