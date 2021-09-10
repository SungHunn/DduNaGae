package com.example.Dde_Na_Gae.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Dde_Na_Gae.Free_Board_Writing;
import com.example.Dde_Na_Gae.R;

public class Freeboard_Fragment extends AppCompatActivity {

    private Button writing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_board_activity);


        writing = (Button) findViewById(R.id.go_writing);

        writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Free_Board_Writing.class);
                startActivity(intent);
            }
        });


    }


}
