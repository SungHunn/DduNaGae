package com.Dde_Na_Gae_final.Dde_Na_Gae.fragment;


import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.Dde_Na_Gae_final.Dde_Na_Gae.R;
import com.Dde_Na_Gae_final.Dde_Na_Gae.model.Article_Model;
import com.Dde_Na_Gae_final.Dde_Na_Gae.model.CommentModel;
import com.Dde_Na_Gae_final.Dde_Na_Gae.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class Dialog_FreeBoard_Comment extends DialogFragment {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ConstraintLayout constraintLayout;

    private ImageView filledheart;
    private ImageView unfilledheart;
    private EditText edittext_comment;
    private Button send;
    private ImageView close;


    RecyclerView recyclerView;
    String uid = user != null ? user.getUid() : null;
    String articleid;

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ActionBar.LayoutParams.MATCH_PARENT;
        params.height = ActionBar.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_freeboard_comment,container,false);

        recyclerView = view.findViewById(R.id.free_board_detail_comment_recyclerview);
        edittext_comment = (EditText)view.findViewById(R.id.free_board_detail_comment);
        send = (Button)view.findViewById(R.id.free_board_detail_post_comment);
        constraintLayout = view.findViewById(R.id.comment_drawer_middle_box);
        close = (ImageView) view.findViewById(R.id.dialog_button_close);
        articleid = getArguments().getString("articleid");


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    Toast.makeText(getActivity(), "로그인한 회원만 이용할 수 있습니다!", Toast.LENGTH_SHORT).show();
                }else{

                    CommentModel commentModel = new CommentModel();
                    commentModel.uid = uid;
                    commentModel.comment = edittext_comment.getText().toString();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    SimpleDateFormat timeformat = new SimpleDateFormat("yyyy년 MM월 dd일  a kk:mm:ss");
                    timeformat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                    commentModel.timestamp = timeformat.format(timestamp);

                    FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            Article_Model article_model = snapshot.getValue(Article_Model.class);
                            if (article_model.have_comment.equals("none")) {
                                FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comments").push().setValue(commentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        edittext_comment.setText("");
                                        set_comment_true(articleid);
                                        checkcomment(articleid);
                                    }
                                });
                            }else{
                                FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comments").push().setValue(commentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        edittext_comment.setText("");
                                    }
                                });
                            }
                            checkcomment(articleid);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        checkcomment(articleid);
        edittext_comment.requestFocus();
        InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        return view;
    }



    public void set_comment_true(String articleid){
        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("have_comment").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { }
                });
    }

    public void checkcomment(String articleid){
        FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("have_comment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.getValue().toString().equals("true") ) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(new Dialog_FreeBoard_Comment.BoardCommentRecyclerViewAdapter());
                    constraintLayout.setVisibility(View.GONE);
                }else{
                    constraintLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



    class BoardCommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<CommentModel> commentModels;
        List<String> commentid;

        public BoardCommentRecyclerViewAdapter(){
            commentModels = new ArrayList<>();
            commentid = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comments").orderByChild("timestamp").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    commentModels.clear();
                    for (DataSnapshot item : snapshot.getChildren()) {
                        CommentModel commentModel = item.getValue(CommentModel.class);
                        commentModels.add(commentModel);
                        commentid.add(item.getKey());
                    }
                    notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
            return new Dialog_FreeBoard_Comment.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            Dialog_FreeBoard_Comment.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder boardCommentViewHolder = ((Dialog_FreeBoard_Comment.BoardCommentRecyclerViewAdapter.BoardCommentViewHolder)holder);



            boardCommentViewHolder.comment_comment.setText(commentModels.get(position).comment);

            FirebaseDatabase.getInstance().getReference().child("users").child(commentModels.get(position).uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    UserModel usermodel = snapshot.getValue(UserModel.class);
                    Glide.with(holder.itemView.getContext())
                            .load(usermodel.imageUri)
                            .apply(new RequestOptions().circleCrop())
                            .into(boardCommentViewHolder.comment_image);

                    boardCommentViewHolder.comment_nickname.setText(usermodel.nickname);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) { }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(commentModels.get(position).uid.equals(uid)){
                        AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());
                        alt_bld.setMessage("삭제하개?").setCancelable(false)
                                .setPositiveButton("삭제하개",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comments").child(commentid.get(position)).setValue(null);
                                                 FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if(snapshot.exists() == false){
                                                            FirebaseDatabase.getInstance().getReference().child("Free_Board").child(articleid).child("have_comment").setValue("none").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    constraintLayout.setVisibility(View.VISIBLE);
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) { }
                                                            });
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                                recyclerView.removeAllViewsInLayout();
                                                recyclerView.setAdapter(new Dialog_FreeBoard_Comment.BoardCommentRecyclerViewAdapter());
                                            }
                                        }).setNegativeButton("아니개",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alt_bld.create();
                        //alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        alert.setTitle("삭제하시겠습니까?");
                        alert.setIcon(R.drawable.logo);
                        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 220, 213)));
                        alert.show();
                    }
                    return false;
                }
            });
        }


        @Override
        public int getItemCount() {
            return commentModels.size();
        }
        private class BoardCommentViewHolder extends RecyclerView.ViewHolder {
            public ImageView comment_image;
            public TextView comment_nickname;
            public TextView comment_comment;


            public BoardCommentViewHolder(View view) {
                super(view);
                comment_comment= view.findViewById(R.id.item_comment_comment);
                comment_nickname= view.findViewById(R.id.item_comment_nickname);
                comment_image = view.findViewById(R.id.item_comment_imageview);

            }

        }
    }


}



