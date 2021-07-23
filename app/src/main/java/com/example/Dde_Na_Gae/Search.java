package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

import android.content.SharedPreferences.Editor;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class Search extends AppCompatActivity {

    public ArrayList<Search_Item> search_items = new ArrayList<>();

    private ListView search_found;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        search_found = findViewById(R.id.search_result);

        searchItemsAdapter().add(new Search_Item("name", "description"));
        searchItemsAdapter().add(new Search_Item("name2", "description2"));
        searchItemsAdapter().add(new Search_Item("name3", "description3"));
        searchItemsAdapter().add(new Search_Item("name4", "description4"));

        search_found.setAdapter(searchItemsAdapter());
        search_found.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), Search_Selected.class);
                intent.putExtra("NAME", search_items.get(position).item_name);
                intent.putExtra("DES", search_items.get(position).item_description);
                startActivity(intent);
            }
        });
    }

    private ArrayAdapter<Search_Item> searchItemsAdapter() {
        ArrayAdapter<Search_Item> adapter = new ArrayAdapter<Search_Item>(getApplicationContext(), R.layout.search_item, search_items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // 배열에서 position 에 위치한 데이터(아이템) 가져오기
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
        return adapter;
    }
}
