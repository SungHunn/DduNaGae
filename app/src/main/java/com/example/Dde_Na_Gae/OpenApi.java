package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class OpenApi extends AppCompatActivity {
    public ListView listView;

    String key = "8BcG%2FMNcIlI4r4BCz1t52mWldmD8sC%2Bqgb57Ent23BrZc2cqqZShLoRAURa3%2BE%2FIZqmEv7PWWZitWmqqaTjU1g%3D%3D";

    ArrayList<String> today_images = new ArrayList<>();
    ArrayList<String> today_titles = new ArrayList<>();
    ArrayList<String> today_contentids = new ArrayList<>();

    ArrayList<String> main_images = new ArrayList<>();
    ArrayList<String> main_titles = new ArrayList<>();
    ArrayList<String> main_contentids = new ArrayList<>();

    ArrayList<String> sub_images = new ArrayList<>();
    ArrayList<String> sub_titles = new ArrayList<>();
    ArrayList<String> sub_contentids = new ArrayList<>();

    Calendar cal = Calendar.getInstance();
    int today = cal.get(Calendar.DAY_OF_MONTH);
    int[] today_region = {1, 2, 31, 32};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
        // 오늘의 place
            new Thread() {
                @Override
                public void run() {
                    today_images.clear();
                    today_titles.clear();
                    today_contentids.clear();
                    int today_index = (today % 4);
                    String urlAdress = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" +
                            "?ServiceKey=" + key +
                            "&MobileOS=AND" +
                            "&MobileApp=TestApp" +
                            "&numOfRows=10" +
                            "&arrange=P" +
                            "&contentTypeId=12" +
                            "&cat1=A01" +
                            "&cat2=A0101" +
                            "&listYN=Y" +
                            "&areaCode=" + today_region[today_index] + // 지역코드
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

//                    System.out.println(jsonData);

                        JSONObject obj = new JSONObject(jsonData);
                        JSONObject response = (JSONObject) obj.get("response");
                        JSONObject test = (JSONObject) response.get("body");
                        JSONObject galUrlResult = (JSONObject) test.get("items");
                        JSONArray galUrl = (JSONArray) galUrlResult.get("item");

                        for (int i = 0; i < galUrl.length(); i++) {
                            JSONObject temp = galUrl.getJSONObject(i);

                            String firstimage = temp.getString("firstimage");
                            String title = temp.getString("title");
                            String contentid = temp.getString("contentid");

                            today_images.add(firstimage);
                            today_titles.add(title);
                            today_contentids.add(contentid);
                        } // for 종료
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 메인으로 인텐트
//                    Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                    intent.putStringArrayListExtra("Today_Image", today_images);
                    intent.putStringArrayListExtra("Today_Title", today_titles);
                    startActivity(intent);
                }
            }.start();
            // 오늘의 place 끝

            // 1 ~ 8 번 (서울, 인천, 부산 등등)
            new Thread(){
                public void run() {
                    int main_index = (today % 8) + 1;
                    String urlAdress = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" +
                            "?ServiceKey=" + key +
                            "&MobileOS=AND" +
                            "&MobileApp=TestApp" +
                            "&numOfRows=10" +
                            "&arrange=P" +
                            "&contentTypeId=12" +
                            "&cat1=A01" +
                            "&cat2=A0101" +
                            "&listYN=Y" +
                            "&areaCode=" + main_index + // 지역코드
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

//                    System.out.println(jsonData);

                        JSONObject obj = new JSONObject(jsonData);
                        JSONObject response = (JSONObject) obj.get("response");
                        JSONObject test = (JSONObject) response.get("body");
                        JSONObject galUrlResult = (JSONObject) test.get("items");
                        JSONArray galUrl = (JSONArray) galUrlResult.get("item");

                        for (int i = 0; i < galUrl.length(); i++) {
                            JSONObject temp = galUrl.getJSONObject(i);

                            String firstimage = temp.getString("firstimage");
                            String title = temp.getString("title");
                            String contentid = temp.getString("contentid");

                            main_images.add(firstimage);
                            main_titles.add(title);
                            main_contentids.add(contentid);
                        } // for 종료
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    Intent main_intent = new Intent(getApplicationContext(), Mainactivity.class);
                    intent.putExtra("Main_Image", main_images);
                    intent.putExtra("Main_Title", main_titles);
                    intent.putExtra("Main_ContentId", main_contentids);
                    startActivity(intent);
                }
            }.start();


            ////  지역 31 ~ 39
            new Thread() {
                @Override
                public void run() {
                    int sub_index = (today % 9) + 31;
                    String urlAdress = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" +
                            "?ServiceKey=" + key +
                            "&MobileOS=AND" +
                            "&MobileApp=TestApp" +
                            "&numOfRows=10" +
                            "&arrange=P" +
                            "&contentTypeId=12" +
                            "&cat1=A01" +
                            "&cat2=A0101" +
                            "&listYN=Y" +
                            "&areaCode=" + sub_index + // 지역코드
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

//                    System.out.println(jsonData);

                        JSONObject obj = new JSONObject(jsonData);
                        JSONObject response = (JSONObject) obj.get("response");
                        JSONObject test = (JSONObject) response.get("body");
                        JSONObject galUrlResult = (JSONObject) test.get("items");
                        JSONArray galUrl = (JSONArray) galUrlResult.get("item");

                        for (int i = 0; i < galUrl.length(); i++) {
                            JSONObject temp = galUrl.getJSONObject(i);

                            String firstimage = temp.getString("firstimage");
                            String title = temp.getString("title");
                            String contentid = temp.getString("contentid");

                            sub_images.add(firstimage);
                            sub_titles.add(title);
                            sub_contentids.add(contentid);
                        } // for 종료
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("Sub_Image", sub_images);
                    intent.putExtra("Sub_Title", sub_titles);
                    intent.putExtra("Sub_ContentId", sub_contentids);
                    startActivity(intent);
                }
            }.start();
    }
}