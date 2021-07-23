package com.example.Dde_Na_Gae;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences.Editor;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class Search extends AppCompatActivity {

    private ArrayList<Search_Item> search_items = new ArrayList<>();
    ArrayAdapter adapter;

    private ListView search_found;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        SaveSearch_item((ArrayList<Search_Item>) search_items);
        ReadSearch_Item();

        //adapter = new ArrayAdapter(this, R.layout.search_item, search_items);

        search_found = findViewById(R.id.search_result);
        search_items = new ArrayList<Search_Item>() {{
            search_items.add(new

                    Search_Item("이름", "이미지", "설명"));
            search_items.add(new

                    Search_Item("이름2", "이미지", "설명"));
            search_items.add(new

                    Search_Item("이름3", "이미지", "설명"));
        }};
        searchItemsAdapter();

    }

    private void searchItemsAdapter() {
        ArrayAdapter<Search_Item> adapter = new ArrayAdapter<Search_Item>(getApplicationContext(), R.layout.search_item, search_items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // 배열에서 position에 위치한 데이터(아이템) 가져오기
                Search_Item search_item = getItem(position);
                // 아이템의 내용을 그림 convertView의 재사용을 위해서 확인
                // null 이면 새로 만들고, null 이 아니면 재사용한다.
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_item, parent, false);
                }
                // ConvertView에서 Card의 내용을 적용할 뷰 찾기
                TextView name = (TextView) convertView.findViewById(R.id.search_name);
                TextView description = (TextView) convertView.findViewById(R.id.search_description);
                // 뷰에 값 지정하기.
                name.setText(search_item.item_name);
                description.setText(search_item.item_description);
                // 완성된 뷰를 반환 ( 화면에 보여주게 된다. )
                return convertView;
            }
        };
        search_found.setAdapter(adapter);
    }

    private void SaveSearch_item(ArrayList<Search_Item> search_items) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(search_items);
        editor.putString("Items", json);
        editor.commit();
    }

    private ArrayList<Search_Item> ReadSearch_Item() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("Items", "EMPTY");
        Type type = new TypeToken<ArrayList<Search_Item>>() {
        }.getType();
        ArrayList<Search_Item> arrayList = gson.fromJson(json, type);
        return arrayList;
    }
}
