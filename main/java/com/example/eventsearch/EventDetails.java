package com.example.eventsearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.eventsearch.EventSummaryList.isFav;
import static com.example.eventsearch.MainActivity.editor;

public class EventDetails extends AppCompatActivity {
    RequestQueue queue;
    JSONObject jsonObject;
    TabLayout tabLayout;
    ViewPager2 pager2;
    DetailFragmentAdapter detailFragmentAdapter;
    String id;

    Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);


        tabLayout = (TabLayout) findViewById(R.id.detailTabs);
        pager2 = (ViewPager2) findViewById(R.id.pager2);


        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.info_outline).setText("EVENTS"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.artist).setText("ARTIST(S)"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.venue).setText("VENUE"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // called when tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // called when a tab is reselected
            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        queue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        getDetailData(id);

    }





    private void getDetailData(String id){
        String url = "https://entertainment-search-01.wl.r.appspot.com/api/getDetails?id="+id;


        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObject=response;
                        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        TextView t=(TextView) findViewById(R.id.eventheader);
                        ImageView i1=(ImageView) findViewById(R.id.twitter);
                        ToggleButton i2 = (ToggleButton) findViewById(R.id.iconfav);
                        i1.setImageResource(R.drawable.ic_twitter_ic__2_);
                        ((Toolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                        try {
                            if(isFav(id)){
                                i2.setChecked(true);
                                i2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill_white));
                            }else{
                                i2.setChecked(false);
                                i2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_outline_white));
                            }
                            if(jsonObject.has("tooltipName"))
                                t.setText(jsonObject.getString("tooltipName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("data", jsonObject.toString());
                        FragmentManager fm = getSupportFragmentManager();
                        detailFragmentAdapter = new DetailFragmentAdapter(fm, getLifecycle(),bundle);
                        pager2.setAdapter(detailFragmentAdapter);

                        i1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = null; // missing 'http://' will cause crashed
                                try {
                                    uri = Uri.parse("https://twitter.com/intent/tweet?text="+"Check%20out%20"+jsonObject.getString("tooltipName")+"%20located%20at%20"+jsonObject.getString("venue")+'.'+"&hashtags=CSCI571EventSearch");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);


                            }});

                        i2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if (!isChecked) {
                                    i2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_outline_white));
                                    editor.remove(id);
                                    editor.commit();
                                }else {
                                    i2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill_white));
                                    FavoritesPOJO fav= null;
                                    try {
                                        fav = new FavoritesPOJO(jsonObject.getString("tooltipName"),jsonObject.getString("venue"),jsonObject.getString("time"),jsonObject.getString("segmentId").toLowerCase(),id);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    gson = new Gson();
                                    String json = gson.toJson(fav);
                                    editor.putString(id, json);
                                    editor.commit();
                                }
                            }
                        });


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }
}