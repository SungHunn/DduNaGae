package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {

    TextView setting_member_id;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        setting_member_id = (TextView)findViewById(R.id.setting_member_id);
        setting_member_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), My_Page.class);
                startActivity(intent);
            }
        });


        final String[] items = {"공지사항", "알림 설정", "비밀번호 변경", "이벤트", "이용약관", "개인정보 처리방식", "탈퇴하기"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        ListView listview = findViewById(R.id.setting_list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent_notice =
                                new Intent(getApplicationContext(), Notice.class);
                        startActivity(intent_notice);
                        break;

                    case 1:
                        Intent intent_alarm =
                                new Intent(getApplicationContext(), Alarm.class);
                        startActivity(intent_alarm);
                        break;

                    case 2:
                        break;

                    case 3:
                        Intent intent_event =
                            new Intent(getApplicationContext(), Notice.class);
                        startActivity(intent_event);

                        break;

                    case 4:
                        Intent intent_terms_of_service =
                                new Intent(getApplicationContext(),Terms_of_service.class);
                        startActivity(intent_terms_of_service);
                        break;

                    case 5:
                        Intent intent_personal_information =
                                new Intent(getApplicationContext(),Personal_information.class);
                        startActivity(intent_personal_information);
                        break;

                    case 6:

                        break;

                }
            }
        });

    }

}
