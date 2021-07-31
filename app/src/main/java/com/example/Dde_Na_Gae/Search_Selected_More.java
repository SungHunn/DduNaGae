package com.example.Dde_Na_Gae;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Search_Selected_More extends AppCompatActivity {

    TextView textView;

    ListView room_info;
    ArrayList<Search_Item> search_items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_selected_more);

        textView = findViewById(R.id.more_page_name);
        textView.setText(getIntent().getStringExtra("More_info"));

        room_info = findViewById(R.id.room_info);
        searchItemsAdapter().add(new Search_Item("객실 1", "설명이에요"));
        searchItemsAdapter().add(new Search_Item("객실 2", "설명이에요"));
        searchItemsAdapter().add(new Search_Item("객실 3", "설명이에요"));
        searchItemsAdapter().add(new Search_Item("객실 4", "설명이에요"));
        searchItemsAdapter().add(new Search_Item("객실 5", "설명이에요"));

        room_info.setAdapter(searchItemsAdapter());
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
