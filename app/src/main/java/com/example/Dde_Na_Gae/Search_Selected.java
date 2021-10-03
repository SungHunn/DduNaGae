 package com.example.Dde_Na_Gae;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.kakaolink.AppActionBuilder;
import com.kakao.kakaolink.AppActionInfoBuilder;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class  Search_Selected extends AppCompatActivity {
    String key = "8BcG%2FMNcIlI4r4BCz1t52mWldmD8sC%2Bqgb57Ent23BrZc2cqqZShLoRAURa3%2BE%2FIZqmEv7PWWZitWmqqaTjU1g%3D%3D";


    ImageView img_back;

    BottomNavigationView bottomNavigationView;

    String img;
    String title;
    String conId;

    String overview;
    String addr1;
    String usetime;

    TextView selected_item_usetime;
    TextView selected_item_desciption;
    TextView selected_item_addr;

    TextView selected_name;
    TextView selected_name_main;

    ImageView selected_img;

    ImageView btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_selected);

        conId = getIntent().getStringExtra("ConId");
        Detail_api detail_api = new Detail_api(conId);
        Thread detail_thread = new Thread(detail_api);
        Info_api info_api = new Info_api(conId);
        Thread info_thread = new Thread(info_api);

        try {
            detail_thread.start();
            detail_thread.join();
            info_thread.start();
            info_thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        overview = stripHtml(detail_api.getOverview());
        addr1 = stripHtml(detail_api.getAddr1());
        usetime = stripHtml(info_api.getUsetime());

        selected_item_usetime = findViewById(R.id.selected_item_usetime);
        selected_item_desciption = findViewById(R.id.selected_item_desciption);
        selected_item_addr = findViewById(R.id.selected_item_addr);

        if (usetime.isEmpty())
            selected_item_usetime.setText("00:00~24:00");
        else
            selected_item_usetime.setText(usetime);

        selected_item_desciption.setText(overview);

        // 내용이 3줄 이상 넘어가면 짜르고 더보기로 표시 -> 클릭시 전체 내용 확인
        makeTextViewResizable(selected_item_desciption, 3, "More", true);
        selected_item_addr.setText(addr1);

        selected_name = findViewById(R.id.selected_name);
//        selected_name.setText(getIntent().getStringExtra("NAME"));
        selected_name_main = findViewById(R.id.selected_name_main);
//        selected_name_main.setText(getIntent().getStringExtra("NAME"));

        title = getIntent().getStringExtra("Title");
        selected_name.setText(title);
        selected_name_main.setText(title);

        img = getIntent().getStringExtra("Image");
        selected_img = (ImageView)findViewById(R.id.selected_img);
        Glide.with(this).load(img).into(selected_img);


//        viewPager2 = findViewById(R.id.selected_img);
//        ViewpagerAdapter adapter = new ViewpagerAdapter(setItem());
//        viewPager2.setAdapter(adapter);

        btn_share = (ImageView)findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.addCategory(Intent.CATEGORY_DEFAULT);
                String text;
                text = "https://youtube.com";
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.setType("text/plain");
//                intent.setPackage("com.kakao.talk");
                startActivity(Intent.createChooser(intent, "공유"));
            }
        });

        // 클릭 이벤트

        // 바텀 네비
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.love_it:
                        //Intent intent = new Intent(getApplicationContext(), Matching.class);
                        //startActivity(intent);
                        break;

                    case R.id.home:
                        Intent intent2 = new Intent(getApplicationContext(), Mainactivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.more_room_activity:
                        Intent intent = new Intent(getApplicationContext(), Search_Selected_More.class);
                        intent.putExtra("More_info", getIntent().getStringExtra("NAME"));
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        img_back = (ImageView)findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mainactivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.matching:
                        Intent intent = new Intent(getApplicationContext(), Matching.class);
                        startActivity(intent);
                        break;

                    case R.id.home:
                        Intent intent2 = new Intent(getApplicationContext(), Mainactivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.mylocation:
                        //Intent intent3 = new Intent(getApplicationContext(), );
                        //startActivity(intent3);
                        break;
                }
                return false;
            }
        });

    }

    public String stripHtml(String html)
    { return Html.fromHtml(html).toString(); }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });
    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "Less", false);
                    } else {
                        makeTextViewResizable(tv, 3, "More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }

    public void shareKakao(){
        try {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            String url = "data:image/png;base64,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";
            kakaoBuilder.addImage(url, 80, 80);
            kakaoBuilder.addText("카카오 링크 테스트");
            kakaoBuilder.addAppButton("앱 설치로 이동", new AppActionBuilder().addActionInfo(AppActionInfoBuilder.createAndroidActionInfoBuilder().setExecuteParam("execparamkey2=2222").setMarketParam("referrer=kakaotalklink").build())
                    .addActionInfo(AppActionInfoBuilder.createiOSActionInfoBuilder(AppActionBuilder.DEVICE_TYPE.PHONE).setExecuteParam("execparamkey2=2222").build())
                    .setUrl("이동할 url을 입력하세요").build());

            kakaoBuilder.setForwardable(true);
            kakaoLink.sendMessage(kakaoBuilder, this);

        } catch (KakaoParameterException e) {
            e.printStackTrace();
        }
    }
}
