package com.Dde_Na_Gae_final.Dde_Na_Gae;

public class Search_Item {
    String item_name;

    String item_description;

    public Search_Item(String name,  String description) {
        super();
        this.item_name = name;

        this.item_description  = description;
    }
    public String setItem_name() {
        return item_name;
    }
    public String setItem_description() {
        return item_description;
    }
}
