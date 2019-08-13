package com.weekender;

public class homelistrow {
    private String[] imageid;
    private String title;

    public homelistrow(String[] imageid, String title) {
        this.imageid = imageid;
        this.title = title;
    }

    public String[] getImageid() {
        return imageid;
    }

    public void setImageid(String[] imageid) {
        this.imageid = imageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
