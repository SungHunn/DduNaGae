package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Api_date extends AppCompatActivity {
    private ListView listView;
    private ListView listView2;

    private Button btnData;
    Button btnArea;

    ArrayAdapter adapter;
    ArrayAdapter adapter2;

    String key = "7lPfYqgSqpO4D6clASln2HERGCK0SJDn%2Bx7xxU4Lekhtu9s6hy5CLEhRYiSzW%2Bsvz0pbh91bio6b2s8fwVW8Mw%3D%3D";

    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> items2 = new ArrayList<>();

    ArrayList<String> city_code = new ArrayList<>();
    ArrayList<String> county_code = new ArrayList<>();

    ArrayList<String> last_code = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_tester);

        btnArea = (Button)findViewById(R.id.area_choice);
        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Search.class);
                intent.putExtra("CITY_CODE", last_code.get(0));
                intent.putExtra("COUNTY_CODE", last_code.get(1));
                startActivity(intent);

                last_code.clear();
            }
        });

        listView = (ListView)findViewById(R.id.listView1);
        listView2 = (ListView)findViewById(R.id.listView2);

        // adapter 스타일 선언 및 items 적용
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items2);
        // listView에 adapter 적용
        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
        btnData = (Button)findViewById(R.id.btnData);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        items.clear();  // items -> ArrayList<String> listView에 넣을 배열
                        String urlAddress = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode" +
                                "?ServiceKey=" + key +
                                "&numOfRows=100" +
                                "&pageNo=1" +
                                "&MobileOS=AND" +
                                "&MobileApp=TestApp" +
                                "&areaCode=" +

                                "&_type=json";
                        try {
                            URL url = new URL(urlAddress);

                            InputStream is = url.openStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader reader = new BufferedReader(isr);

                            StringBuffer buffer = new StringBuffer();
                            String line = reader.readLine();
                            while (line != null) {
                                buffer.append(line + "\n");
                                line = reader.readLine();
                            }

                            String jsonData = buffer.toString();

                            System.out.println(jsonData);

                            JSONObject obj = new JSONObject(jsonData);
                            JSONObject response = (JSONObject)obj.get("response");
                            JSONObject test = (JSONObject)response.get("body");
                            JSONObject areaCodeResult = (JSONObject)test.get("items");
                            JSONArray areaCode = (JSONArray)areaCodeResult.get("item");

                            for (int i = 0; i < areaCode.length(); i++) {

                                JSONObject temp = areaCode.getJSONObject(i);

                                String name = temp.getString("name");
                                String code = temp.getString("code");

                                city_code.add(code);
                                items.add(name);

                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = (String)parent.getItemAtPosition(position);
                final String num = city_code.get(position);

                last_code.add(city_code.get(position));
                Toast.makeText(Api_date.this, data, Toast.LENGTH_SHORT).show();

                new Thread(){
                    @Override
                    public void run() {
                        items2.clear();  // items -> ArrayList<String> listView에 넣을 배열
                        String urlAddress = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode" +
                                "?ServiceKey=" + key +
                                "&numOfRows=100" +
                                "&pageNo=1" +
                                "&MobileOS=AND" +
                                "&MobileApp=TestApp" +
                                "&areaCode=" + num +

                                "&_type=json";
                        try {
                            URL url = new URL(urlAddress);

                            InputStream is = url.openStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader reader = new BufferedReader(isr);

                            StringBuffer buffer = new StringBuffer();
                            String line = reader.readLine();
                            while (line != null) {
                                buffer.append(line + "\n");
                                line = reader.readLine();
                            }

                            String jsonData = buffer.toString();

                            System.out.println(jsonData);

                            JSONObject obj = new JSONObject(jsonData);
                            JSONObject response = (JSONObject)obj.get("response");
                            JSONObject test = (JSONObject)response.get("body");
                            JSONObject areaCodeResult = (JSONObject)test.get("items");
                            JSONArray areaCode = (JSONArray)areaCodeResult.get("item");

                            for (int i = 0; i < areaCode.length(); i++) {

                                JSONObject temp = areaCode.getJSONObject(i);

                                String name = temp.getString("name");
                                String code = temp.getString("code");

                                county_code.add(code);  //county_code <- ArrayList
                                items2.add(name);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter2.notifyDataSetChanged();
                                }
                            });

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                last_code.add(county_code.get(position));
                for(int i = 0; i < last_code.size(); i++) {
                    System.out.println(last_code.get(i));
                }
            }
        });
    }
}