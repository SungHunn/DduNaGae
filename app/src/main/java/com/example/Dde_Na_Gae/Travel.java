package com.example.Dde_Na_Gae;

import java.io.Serializable;

public class Travel {
    private String url;
    private String title;
    private int conId;

    public Travel(String url, String title, int conId){
        this.url = url;
        this.title = title;
        this.conId = conId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getConId() {
        return conId;
    }

    public void setConId(int conId) {
        this.conId = conId;
    }
}
