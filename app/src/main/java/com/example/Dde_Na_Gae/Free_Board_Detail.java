package com.example.Dde_Na_Gae;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.Dde_Na_Gae.fragment.Dialog_FreeBoard_Comment;
import com.example.Dde_Na_Gae.model.Article_Model;
import com.example.Dde_Na_Gae.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Free_Board_Detail  extends AppCompatActivity {

    private TextView title;
    private TextView content;
    private TextView nickname;
    private TextView love_it_num;
    private TextView comment_num;



    private TextView heart_count;
    private TextView comment_count;

    private TextView comment_view;

    LinearLayout linearLayout;



    int check = 0;

    private ImageView filledheart;
    private ImageView unfilledheart;
    private ImageView photo;
    private ImageView profile;
    private ImageView go_back;



    private String articleid;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user != null ? user.getUid() : null;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freeboard_detail);

        uid = FirebaseAuth.getInstance().getUid();
        Intent intent = getIntent();
        articleid =  intent.getStringExtra("articleid");

        linearLayout = findViewById(R.id.free_board_detail_iloveit);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        comment_view = findViewById(R.id.free_board_detail_comment_view);
        comment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("articleid",articleid);
                Dialog_FreeBoard_Comment bottom_sheet_freeBoard_comment = new Dialog_FreeBoard_Comment();
                bottom_sheet_freeBoard_comment.setArguments(bundle);
                bottom_sheet_freeBoard_comment.show(getSupportFragmentManager(),"dialog_event");
            }
        });


        title = (TextView)findViewById(R.id.free_board_detail_title) ;
        content = (TextView)findViewById(R.id.free_board_detail_content);
        nickname = (TextView)findViewById(R.id.writer_nickname);
        love_it_num = findViewById(R.id.love_it_num);
        comment_num = findViewById(R.id.comment_num);




        filledheart = findViewById(R.id.filled_heart);
        unfilledheart = findViewById(R.id.unfilled_heart);
        photo = (ImageView)findViewById(R.id.free_board_image);
        profile = (ImageView)findViewById(R.id.freeboard_detail_myprofile);


        go_back = (ImageView)findViewById(R.id.freeboard_detail_img_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Freeboard_Activity.class);
                startActivity(intent);
            }
        });
        heart_count = (TextView)findViewById(R.id.love_it_num);
        comment_count = (TextView)findViewById(R.id.comment_num);
        if (user != null) {
            checkloveit(articleid,uid);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    Toast.makeText(Free_Board_Detail.this, "로그인한 회원만 이용할 수 있습니다!", Toast.LENGTH_SHORT).show();
                }else{
                    UserModel usermodels = new UserModel();
                    if(unfilledheart.getVisibility() == View.VISIBLE) {
                        usermodels.uid = uid;
                        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").push().setValue(usermodels);
                        checkloveit(articleid, uid);
                    }else{
                        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").orderByChild("uid").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot item : snapshot.getChildren()){
                                    UserModel userModel = item.getValue(UserModel.class);
                                    if(userModel.uid.equals(uid)){
                                        String key = item.getKey();
                                        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").child(key).setValue(null);
                                        filledheart.setVisibility(View.GONE);
                                        unfilledheart.setVisibility(View.VISIBLE);
                                        break;
                                    }

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
        });

        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Article_Model article ;
                article = snapshot.getValue(Article_Model.class);
                title.setText(article.title);
                content.setText(article.content);
                nickname.setText(article.nickname);

                Glide.with(Free_Board_Detail.this)
                        .load(article.imageUri)
                        .apply(new RequestOptions().circleCrop())
                        .into(photo);

                FirebaseDatabase.getInstance().getReference().child("users").child(article.uid).child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        String uri = snapshot.getValue(String.class);

                        Glide.with(Free_Board_Detail.this)
                                .load(uri)
                                .apply(new RequestOptions().circleCrop())
                                .into(profile);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                List<String> counting = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()){

                    counting.add(item.getKey());

                }
                if (counting.size() == 0 ){
                    heart_count.setText(" ");
                }else{
                    heart_count.setText(String.valueOf(counting.size())+ " 개");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                List<String> counting = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()){
                    counting.add(item.getKey());
                }
                if (counting.size() == 0 ){
                    comment_count.setText(" ");
                }else{
                    comment_count.setText(String.valueOf(counting.size()) + " 개");
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




    }



    public void checkloveit(String articleid, String uid){
        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").orderByChild("uid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            UserModel userModel;
             for(DataSnapshot item : snapshot.getChildren()){
                 userModel = item.getValue(UserModel.class);
                 if(userModel.uid.equals(uid)){
                     filledheart.setVisibility(View.VISIBLE);
                     unfilledheart.setVisibility(View.GONE);
                     FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                             List<String> counting = new ArrayList<>();

                             for (DataSnapshot item : snapshot.getChildren()){

                                 counting.add(item.getKey());

                             }
                             if (counting.size() == 0 ){
                                 heart_count.setText(" ");
                             }else{
                                 heart_count.setText(String.valueOf(counting.size())+ " 개");
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull @NotNull DatabaseError error) {

                         }
                     });
                     break;
                 }else{
                     filledheart.setVisibility(View.GONE);
                     unfilledheart.setVisibility(View.VISIBLE);
                     FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("Loveit").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                             List<String> counting = new ArrayList<>();

                             for (DataSnapshot item : snapshot.getChildren()){

                                 counting.add(item.getKey());

                             }
                             if (counting.size() == 0 ){
                                 heart_count.setText(" ");
                             }else{
                                 heart_count.setText(String.valueOf(counting.size())+ " 개");
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull @NotNull DatabaseError error) {

                         }
                     });
                     break;
                 }
             }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) { }
        });
    }





}

