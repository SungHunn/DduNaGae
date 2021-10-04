package com.example.Dde_Na_Gae;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Detail_api implements Runnable {
    String key = "8BcG%2FMNcIlI4r4BCz1t52mWldmD8sC%2Bqgb57Ent23BrZc2cqqZShLoRAURa3%2BE%2FIZqmEv7PWWZitWmqqaTjU1g%3D%3D";
    String conId;

    String overview;
    String addr1;

    public Detail_api(String conId) {
        this.conId = conId;
    }

    @Override
    public void run() {
        String urlAdress = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon" +
                "?serviceKey=" + key +
                "&numOfRows=10" +
                "&pageNo=1" +
                "&MobileOS=AND" +
                "&MobileApp=AppTest" +
                "&contentId=" + conId +
                "&contentTypeId=12" +
                "&defaultYN=Y" +
                "&firstImageYN=Y" +
                "&areacodeYN=Y" +
                "&catcodeYN=Y" +
                "&addrinfoYN=Y" +
                "&mapinfoYN=Y" +
                "&overviewYN=Y" +
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

            JSONObject obj = new JSONObject(jsonData);
            JSONObject response = (JSONObject) obj.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONObject temp = items.getJSONObject("item");

//                    System.out.println(temp);

            addr1 = temp.getString("addr1");  // 주소 받아오기
            overview = temp.getString("overview"); // 개요(설명) 받아오기
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getOverview() {
        return overview;
    }

    public String getAddr1() {
        return addr1;
    }
}