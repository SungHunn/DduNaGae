package com.example.Dde_Na_Gae;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TravelDB {

    private static Map<String, Travel> db = new HashMap<String, Travel>();

    public static void addData(String index, Travel travel){
        db.put(index, travel);
    }

    public static Travel getData(String index){
        return db.get(index);
    }

    public static List<String> getIndexes(){
        Iterator<String> keys = db.keySet().iterator();

        List<String> keyList = new ArrayList<String>();
        String key = "";
        while(keys.hasNext()){
            key = keys.next();
            keyList.add(key);
        }
        return keyList;
    }
}
