package com.example.Dde_Na_Gae.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Dde_Na_Gae.Mainactivity;
import com.example.Dde_Na_Gae.Matching_Option;
import com.example.Dde_Na_Gae.Matching_filter_Option;
import com.example.Dde_Na_Gae.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Matching_Fragment extends Fragment {
    ImageView img_back;
    Button btn_match_find;
    Button btn_make_match;
    Button btn_my_group;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matching,container,false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        img_back = (ImageView)view.findViewById(R.id.img_back);
        btn_match_find = (Button)view.findViewById(R.id.btn_match_find);
        btn_make_match = (Button)view.findViewById(R.id.btn_matchmake);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Mainactivity.class);
                startActivity(intent);
            }
        });

        btn_match_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Matching_filter_Option.class);
                startActivity(intent);
            }
        });
        btn_make_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    Toast.makeText(getActivity(), "로그인한 회원만 이용할 수 있습니다!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), Matching_Option.class);
                    startActivity(intent);
                }


            }
        });

        return view;
    }


}
