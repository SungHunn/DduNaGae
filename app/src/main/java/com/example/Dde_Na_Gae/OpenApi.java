package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class OpenApi extends AppCompatActivity {
    public ListView listView;

    ArrayAdapter adapter;

    String key = "8BcG%2FMNcIlI4r4BCz1t52mWldmD8sC%2Bqgb57Ent23BrZc2cqqZShLoRAURa3%2BE%2FIZqmEv7PWWZitWmqqaTjU1g%3D%3D";

    ArrayList<String> iamges = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread() {  // 서울
            @Override
            public void run() {
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
                        "&areaCode=1" + // 지역코드
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

                    for (int i = 0; i < 2; i++) {
                        JSONObject temp = galUrl.getJSONObject(i);

                        String firstimage = temp.getString("firstimage");
                        String title = temp.getString("title");

                        iamges.add(firstimage);
                        titles.add(title);
                    } // for 종료
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                intent.putExtra("Image", iamges);
                intent.putExtra("Title", titles);
                startActivity(intent);
            }
        }.start();

        new Thread(){   //경기도
            public void run() {
                iamges.clear();
                titles.clear();
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
                        "&areaCode=31" + // 지역코드
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

                    for (int i = 0; i < 2; i++) {
                        JSONObject temp = galUrl.getJSONObject(i);

                        String firstimage = temp.getString("firstimage");
                        String title = temp.getString("title");

                        iamges.add(firstimage);
                        titles.add(title);
                    } // for 종료
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                intent.putExtra("Image", iamges);
                intent.putExtra("Title", titles);
                startActivity(intent);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                iamges.clear();
                titles.clear();
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
                        "&areaCode=4" + // 지역코드
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

                    for (int i = 0; i < 2; i++) {
                        JSONObject temp = galUrl.getJSONObject(i);

                        String firstimage = temp.getString("firstimage");
                        String title = temp.getString("title");

                        iamges.add(firstimage);
                        titles.add(title);
                    } // for 종료
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                intent.putExtra("Image", iamges);
                intent.putExtra("Title", titles);
                startActivity(intent);
            }
        }.start();
    }
}