package com.example.Dde_Na_Gae;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api_Tester {
    public static void main(String[] args) throws IOException {
        String key = "7lPfYqgSqpO4D6clASln2HERGCK0SJDn%2Bx7xxU4Lekhtu9s6hy5CLEhRYiSzW%2Bsvz0pbh91bio6b2s8fwVW8Mw%3D%3D";

        URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode" +
                "?ServiceKey=" + key +
                "&numOfRows=10" +
                "&pageNo=1" +
                "&MobileOS=AND" +
                "&MobileApp=TestApp" +
                "&areaCode=" +
                "&contentTypeId=12" +
                "&cat1=A01" +
                "&cat2=A0101" +
                "&_type=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line = "";

        while ((line = rd.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        try {

            String result = "";

            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            result = bf.readLine();


            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject test = (JSONObject)jsonObject.get("body");
            JSONObject areaInfoResult = (JSONObject) test.get("items");
            JSONArray areaInfo = (JSONArray) areaInfoResult.get("item");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
