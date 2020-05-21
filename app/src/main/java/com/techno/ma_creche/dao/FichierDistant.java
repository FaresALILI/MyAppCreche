package com.techno.ma_creche.dao;

public class FichierDistant {
    String link;
    String description;
    String typeActivity;
    String extFile;

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public String getTypeActivity() {
        return typeActivity;
    }

    public String getExtFile() {
        return extFile;
    }

    public FichierDistant(String link, String description, String typeActivity, String extFile) {
        this.link = link;
        this.description = description;
        this.typeActivity = typeActivity;
        this.extFile = extFile;
    }

    public FichierDistant() {
    }
}
