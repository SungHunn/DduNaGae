package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Search extends AppCompatActivity {

    public ArrayList<Search_Item> search_items = new ArrayList<>();

    private ListView search_found;
    Button btnArea;

    String city_code;
    String county_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        search_found = findViewById(R.id.search_result);
        btnArea = findViewById(R.id.area_choice);
        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Api_date.class);
                startActivity(intent);
            }
        });

        searchItemsAdapter().add(new Search_Item("item1", "disciption1"));
        searchItemsAdapter().add(new Search_Item("item2", "disciption2"));
        searchItemsAdapter().add(new Search_Item("item3", "disciption3"));
        searchItemsAdapter().add(new Search_Item("item4", "disciption4"));

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

        city_code = getIntent().getStringExtra("CITY_CODE");
        county_code = getIntent().getStringExtra("COUNTY_CODE");
    }

    private ArrayAdapter<Search_Item> searchItemsAdapter() {
        ArrayAdapter<Search_Item> adapter = new ArrayAdapter<Search_Item>(getApplicationContext(), R.layout.search_item, search_items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Search_Item search_item = getItem(position);
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_item, parent, false);
                }
//                TextView name = (TextView) convertView.findViewById(R.id.search_name);
////                TextView description = (TextView) convertView.findViewById(R.id.search_description);
//                name.setText(search_item.item_name);
//                description.setText(search_item.item_description);
                return convertView;
            }
        };
        return adapter;
    }
}
