package com.example.Dde_Na_Gae;

import java.io.Serializable;
import java.util.ArrayList;

public class Travel {
    private ArrayList<String> url = new ArrayList<String>();
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> conId = new ArrayList<String>();

    public Travel(ArrayList<String> url, ArrayList<String> title, ArrayList<String> conId){
        this.url = url;
        this.title = title;
        this.conId = conId;
    }

    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url.add(url);
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.add(title);
    }

    public ArrayList<String> getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId.add(conId);
    }
}
