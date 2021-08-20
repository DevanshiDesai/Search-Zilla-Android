package com.example.eventsearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.eventsearch.MainActivity.sharedPreference;


public class EventSummaryList extends AppCompatActivity {


    ArrayList<EventSummaryResult> eventArray=new ArrayList<>();
    RequestQueue queue;
    ArrayList<String> title=new ArrayList<>();
    ArrayList<String> subTitle=new ArrayList<>();
    ArrayList<String> date=new ArrayList<>();
    ArrayList<String> id=new ArrayList<>();
    ArrayList<String> segmentId=new ArrayList<>();
    ListViewAdapter adapter;
    ArrayList<Boolean> favorites=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_summary_list);
        TextView t=(TextView) findViewById(R.id.eventheader);
        t.setText("Search Results");
        ToggleButton i1= (ToggleButton) findViewById(R.id.iconfav);
        i1.setVisibility(View.GONE);
        ImageView i2= (ImageView) findViewById(R.id.twitter);
        i2.setVisibility(View.GONE);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        queue = Volley.newRequestQueue(this);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        getResultData(intent.getStringExtra("lat"),intent.getStringExtra("lng"),intent.getStringExtra("distance"),intent.getStringExtra("keyword"),intent.getStringExtra("units"),intent.getStringExtra("category"));

        Log.i("EventSummaryList","onCreateStarted");

        RecyclerView recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter=new ListViewAdapter(this,title,subTitle,date,id,segmentId,favorites,"list");
        recyclerView.setAdapter(adapter);

    }

    public static  Boolean isFav(String id){
        return sharedPreference.contains(id);
    }


    public void getResultData(String lat,String lng,String distance,String keyword,String units,String category){
        Gson gson = new Gson();
//        Log.i("distance value",distance);

        //default condition and change on selected in dropdown
        String url = "https://entertainment-search-01.wl.r.appspot.com/api/getResultsData?"
                +"distance="+ distance+"&units="+units+"&keyword="+keyword+"&lat="+lat+"&long="+lng;
        if(!category.equals("default")){
            url=url+"&category="+category;
        }else{
            url=url+"&category=default";
        }
        Log.i("URL",url);
        // Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        title.clear();
                        subTitle.clear();
                        date.clear();
                        id.clear();
                        favorites.clear();
                        segmentId.clear();
                        for(int i=0; i < response.length(); i++){
                            try {
                                JSONObject res = response.getJSONObject(i);
                                EventSummaryResult e=gson.fromJson(res.toString(), EventSummaryResult.class);
//                                eventArray[i]=e;
                                title.add(e.getName());
                                subTitle.add(e.getVenue());
                                date.add(e.getDate());
                                id.add(e.getId());
                                favorites.add(isFav(e.getId()));
                                segmentId.add(e.getSegmentId());
                                Log.e("title_tags", title.get(title.size() - 1));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
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