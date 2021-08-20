package com.example.eventsearch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private JSONObject jsonObject=null;
    ViewGroup view;

    public VenueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VenueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VenueFragment newInstance(String param1, String param2) {
        VenueFragment fragment = new VenueFragment();
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
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            try {
                jsonObject= new JSONObject(getArguments().getString("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void init() throws JSONException{
        TextView name=(TextView) view.findViewById(R.id.name);
        TextView address=(TextView) view.findViewById(R.id.address);
        TextView city=(TextView) view.findViewById(R.id.city);
        TextView phoneNo=(TextView) view.findViewById(R.id.phoneNo);
        TextView openHours=(TextView) view.findViewById(R.id.openHours);
        TextView generalRule=(TextView) view.findViewById(R.id.generalRule);
        TextView childRule=(TextView) view.findViewById(R.id.childRule);

        System.out.println(jsonObject.toString());
        if(jsonObject.has("venue")) {
            name.setText(jsonObject.getString("venue"));
        }else{
            TextView nameLabel=(TextView) view.findViewById(R.id.nameLabel);
            name.setVisibility(View.GONE);
            nameLabel.setVisibility(View.GONE);
        }
        if(jsonObject.has("address")) {
            address.setText(jsonObject.getString("address"));
        }else{
            TextView addressLabel=(TextView) view.findViewById(R.id.addressLabel);
            address.setVisibility(View.GONE);
            addressLabel.setVisibility(View.GONE);
        }
        if(jsonObject.has("city")) {
            city.setText(jsonObject.getString("city"));
        }else{
            TextView cityLabel=(TextView) view.findViewById(R.id.cityLabel);
            city.setVisibility(View.GONE);
            cityLabel.setVisibility(View.GONE);
        }
        if(jsonObject.has("phoneNo")) {
            phoneNo.setText(jsonObject.getString("phoneNo"));
        }else{
            TextView phoneNoLabel=(TextView) view.findViewById(R.id.phoneNoLabel);
            phoneNo.setVisibility(View.GONE);
            phoneNoLabel.setVisibility(View.GONE);
        }
        if(jsonObject.has("openHours")) {
            openHours.setText(jsonObject.getString("openHours"));
        }else{
            TextView openHoursLabel=(TextView) view.findViewById(R.id.openHoursLabel);
            openHours.setVisibility(View.GONE);
            openHoursLabel.setVisibility(View.GONE);
        }
        if(jsonObject.has("generalRule")) {
            generalRule.setText(jsonObject.getString("generalRule"));
        }else{
            TextView generalRuleLabel=(TextView) view.findViewById(R.id.generalRuleLabel);
            generalRule.setVisibility(View.GONE);
            generalRuleLabel.setVisibility(View.GONE);
        }
        if(jsonObject.has("childRule")) {
            childRule.setText(jsonObject.getString("childRule"));
        }else{
            TextView childRuleLabel=(TextView) view.findViewById(R.id.childRuleLabel);
            childRule.setVisibility(View.GONE);
            childRuleLabel.setVisibility(View.GONE);
        }
    }


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            System.out.print(jsonObject.toString());
            if(jsonObject.has("venueLat") && jsonObject.has("venueLong") ) {
                LatLng loc = null;
                try {
                    loc = new LatLng(Float.valueOf(jsonObject.getString("venueLat")), Float.valueOf(jsonObject.getString("venueLong")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                googleMap.addMarker(new MarkerOptions().position(loc).title("Marker on location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,14.0f));
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=(ViewGroup) inflater.inflate(R.layout.fragment_venue, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            init();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}