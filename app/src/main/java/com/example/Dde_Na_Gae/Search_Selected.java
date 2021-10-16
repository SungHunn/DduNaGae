package com.example.Dde_Na_Gae;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.kakao.util.KakaoParameterException;

import org.jetbrains.annotations.NotNull;
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
    ImageView selected_back;

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

    String url;
    GpsTracker gpsTracker;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_selected);

        Context context = this;

        conId = getIntent().getStringExtra("conId");
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

        // 바텀 네비
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        Toast.makeText(Search_Selected.this, "카카오맵으로 이동합니다.", Toast.LENGTH_SHORT).show();

                        //sp = 출발지, ep = 도착지
                        Location end_point;
                        end_point = addrToPoint(context, title);

                        gpsTracker = new GpsTracker(Search_Selected.this);
                        latitude = gpsTracker.latitude;
                        longitude = gpsTracker.longitude;
                        url = "kakaomap://route?" +
                                "sp=" + latitude + "," + longitude +
                                "&ep=" + end_point.getLatitude() +"," + end_point.getLongitude() + "&by=CAR";

                        System.out.println(latitude + ", " + longitude);

                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent1);
                        break;

                    case R.id.home:
                        Intent intent2 = new Intent(getApplicationContext(), Mainactivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.share:
                        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                                .setLink(Uri.parse("https://search.daum.net/search?nil_suggest=btn&w=tot&DA=SBC&q=" + title))
                                .setDomainUriPrefix("https://ddunagae.page.link/mVFa/" + conId)
                                // Open links with this app on Android
                                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                                .buildDynamicLink();

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        Uri dynamicLinkUri = dynamicLink.getUri();
                        String msg = "여기로 떠날까요?\n" + dynamicLinkUri.toString();
                        intent.putExtra(Intent.EXTRA_TEXT, msg);
                        intent.setType("text/plain");
                        startActivity(intent);

                        break;
                }
                return false;
            }
        });

        selected_back = (ImageView)findViewById(R.id.selected_back);
        selected_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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