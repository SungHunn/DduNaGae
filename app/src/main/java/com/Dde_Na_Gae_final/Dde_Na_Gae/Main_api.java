package com.Dde_Na_Gae_final.Dde_Na_Gae;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Main_api implements Runnable{

    String key = "8BcG%2FMNcIlI4r4BCz1t52mWldmD8sC%2Bqgb57Ent23BrZc2cqqZShLoRAURa3%2BE%2FIZqmEv7PWWZitWmqqaTjU1g%3D%3D";

    ArrayList<String> main_urls = new ArrayList<>();
    ArrayList<String> main_titles = new ArrayList<>();
    ArrayList<String> main_contentids = new ArrayList<>();
    ArrayList<String> main_addrs = new ArrayList<>();

    String region_code;

    public Main_api(String region_code){
        this.region_code = region_code;
    }

    @Override
    public void run() {
        String urlAdress = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" +
                "?ServiceKey=" + key +
                "&MobileOS=AND" +
                "&MobileApp=TestApp" +
                "&numOfRows=100" +
                "&arrange=P" +
                "&contentTypeId=12" +
                "&cat1=A01" +
                "&cat2=A0101" +
                "&listYN=Y" +
                "&areaCode=" + region_code + // 지역코드
                "&_type=json";
        try {
            URL url = new URL(urlAdress);

            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = br.readLine();

            while (line != null) {
                buffer.append(line + "\n");
                line = br.readLine();
            }

            String jsonData = buffer.toString();
//            System.out.println(jsonData);

            JSONObject obj = new JSONObject(jsonData);
            JSONObject response = (JSONObject) obj.get("response");
            JSONObject test = (JSONObject) response.get("body");
            JSONObject galUrlResult = (JSONObject) test.get("items");
            JSONArray galUrl = (JSONArray) galUrlResult.get("item");

            for (int i = 0; i < galUrl.length(); i++) {
                JSONObject temp = galUrl.getJSONObject(i);
                String firstimage = temp.getString("firstimage");
                String title = temp.getString("title");
                String addr = temp.getString("addr1");
                String contentid = temp.getString("contentid");

                if (title.contains("(")){
                    title = title.substring(0, title.indexOf("("));
                }

                // 두번째 "공백" 인덱스 값
                int index = addr.indexOf(" ", addr.indexOf(" ")+1);
                addr = addr.substring(0, index);

                main_urls.add(firstimage);
                main_titles.add(title);
                main_contentids.add(contentid);
                main_addrs.add(addr);

            } // for 종료
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getMain_urls() {
        return main_urls;
    }

    public ArrayList<String> getMain_titles() {
        return main_titles;
    }

    public ArrayList<String> getMain_contentids() {
        return main_contentids;
    }

    public ArrayList<String> getMain_addrs() {
        return main_addrs;
    }
}