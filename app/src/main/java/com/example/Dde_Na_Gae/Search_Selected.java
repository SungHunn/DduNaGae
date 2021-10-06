package com.example.Dde_Na_Gae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.telephony.CarrierConfigManager;
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
import java.util.List;

public class  Search_Selected extends AppCompatActivity {
    String key = "8BcG%2FMNcIlI4r4BCz1t52mWldmD8sC%2Bqgb57Ent23BrZc2cqqZShLoRAURa3%2BE%2FIZqmEv7PWWZitWmqqaTjU1g%3D%3D";
    ImageView img_back;

    BottomNavigationView bottomNavigationView;

    String img;
    public String title;
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
    String url;
    GpsTracker gpsTracker;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_selected);

        Context context = this;

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
        selected_name_main = findViewById(R.id.selected_name_main);

        title = detail_api.getTitle();
        selected_name.setText(title);
        selected_name_main.setText(title);

        img = detail_api.getImg_url();
        selected_img = (ImageView)findViewById(R.id.selected_img);
        Glide.with(this).load(img).into(selected_img);

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

        // 바텀 네비
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        //sp = 출발지, ep = 도착지
                        Location end_point;
                        end_point = addrToPoint(context, title);

                        gpsTracker = new GpsTracker(Search_Selected.this);
                        latitude = gpsTracker.latitude;
                        longitude = gpsTracker.longitude;
                        url = "kakaomap://route?" +
                                "sp=" + latitude + "," + longitude +
                                "&ep=" + end_point.getLatitude() +"," + end_point.getLongitude() + "&by=CAR";


                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent1);
                        break;

                    case R.id.home:
                        Intent intent2 = new Intent(getApplicationContext(), Mainactivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.more_room_activity:
                        Intent intent3 = new Intent(getApplicationContext(), Search_Selected_More.class);
                        intent3.putExtra("More_info", getIntent().getStringExtra("NAME"));
                        startActivity(intent3);
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

    public static Location addrToPoint(Context context, String title) {
        List<Address> addresses = null;

        Location location = new Location("");
        Geocoder geocoder = new Geocoder(context);

        try {
            addresses = geocoder.getFromLocationName(title, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null){
            for(int i = 0; i < addresses.size() ; i ++) {
                Address lating = addresses.get(i);
                location.setLatitude(lating.getLatitude());
                location.setLongitude(lating.getLongitude());
            }
        }
        return location;
    }

}