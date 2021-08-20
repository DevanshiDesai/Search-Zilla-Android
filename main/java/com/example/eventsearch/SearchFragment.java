package com.example.eventsearch;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.eventsearch.MainActivity.clat;
import static com.example.eventsearch.MainActivity.clong;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText keyword;
    EditText distance;
    Spinner categorySpinner;
    Spinner unitsSpinner;
    RadioButton currentRadio;
    RadioButton otherRadio;
    EditText otherText;
    Button search;
    Button clear;
    RequestQueue queue;
    private LocationManager locationManager;
    private String lat;
    private String lng;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        keyword = (EditText) root.findViewById(R.id.keyword);
        distance = (EditText) root.findViewById(R.id.distance);
        categorySpinner = (Spinner) root.findViewById(R.id.categorySpinner);
        unitsSpinner = (Spinner) root.findViewById(R.id.unitsSpinner);
        currentRadio = (RadioButton) root.findViewById(R.id.currentRadio);
        otherRadio = (RadioButton) root.findViewById(R.id.otherRadio);
        otherText = (EditText) root.findViewById(R.id.otherText);
        search = (Button) root.findViewById(R.id.search_go_btn);
        clear = (Button) root.findViewById(R.id.reset);
        queue = Volley.newRequestQueue(getContext());

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categoryArray, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> unitsAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.units, android.R.layout.simple_spinner_item);
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsSpinner.setAdapter(unitsAdapter);

        currentRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentRadio.isChecked()){
                    otherText.setText("");
                    otherText.setEnabled(false);
                }
            }
        });

        otherRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otherRadio.isChecked()){
                    otherText.setEnabled(true);
                    otherText.requestFocus();
                }
            }
        });





        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyword.getText().toString().trim().equals("")) {
                    keyword.setError("Please enter mandatory field");
                    keyword.requestFocus();
                }
                if (otherText.getText().toString().trim().equals("") && otherRadio.isChecked() == true) {
                    otherText.setError("Please enter mandatory field");
                    otherText.requestFocus();
                }
                if (keyword.getError() == null && otherText.getError() == null) {
                    // Instantiate the RequestQueue.

                    if(otherRadio.isChecked()){
                        String location=otherText.getText().toString();
                        String url="https://entertainment-search-01.wl.r.appspot.com/api/getEnteredLoc?location="+location;
                        Log.i("URL",url);
                        // Request a string response from the provided URL.
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String lat=(String) response.getString("lat");
                                            String lng=(String) response.getString("lng");
                                            sendMessage(view,lat,lng, distance.getText().toString(),keyword.getText().toString(),unitsSpinner.getSelectedItem().toString().toLowerCase(),categorySpinner.getSelectedItem().toString().toLowerCase());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                        queue.add(request);
                    }else{
                        String lat=clat.toString();
                        String lng=clong.toString();
                        sendMessage(view,lat,lng, distance.getText().toString(),keyword.getText().toString(),unitsSpinner.getSelectedItem().toString().toLowerCase(),categorySpinner.getSelectedItem().toString().toLowerCase());
                    }

                }
            }
        });



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword.setText("");
                distance.setText("10");
                currentRadio.setChecked(true);
                otherRadio.setChecked(false);
                categorySpinner.setSelection(0);
                unitsSpinner.setSelection(0);
                otherText.setText("");
                otherText.setEnabled(false);
                keyword.setError(null);
                otherText.setError(null);
            }
        });


        return root;



    }



    public void sendMessage(View view,String lat,String lng,String distance,String keyword,String units,String category) {
        Intent intent = new Intent(getActivity(), EventSummaryList.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lng",lng);
        intent.putExtra("distance",distance);
        intent.putExtra("keyword",keyword);
        intent.putExtra("units",units);
        String segmentId="default";
        switch(category){
            case "sports":
                segmentId="KZFzniwnSyZfZ7v7nE";
                break;
            case "music":
                segmentId="KZFzniwnSyZfZ7v7nJ";
                break;
            case "art & theatre":
                segmentId="KZFzniwnSyZfZ7v7na";
                break;
            case "film":
                segmentId="KZFzniwnSyZfZ7v7nn";
                break;
            case "miscellaneous":
                segmentId="KZFzniwnSyZfZ7v7n1";
                break;
        }


        intent.putExtra("category",segmentId);
        startActivity(intent);
    }


}