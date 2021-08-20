package com.example.eventsearch;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private JSONObject jsonObject=null;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InfoFragment() {
        // Required empty public constructor
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

    public void init(View view) throws JSONException{
        TextView artist=(TextView) view.findViewById(R.id.artist);
        TextView venue=(TextView) view.findViewById(R.id.venue);
        TextView date=(TextView) view.findViewById(R.id.date);
        TextView category=(TextView) view.findViewById(R.id.category);
        TextView priceRange=(TextView) view.findViewById(R.id.priceRange);
        TextView ticketStatus=(TextView) view.findViewById(R.id.ticketStatus);
        TextView buyTicketAt=(TextView) view.findViewById(R.id.buyTicketAt);
        TextView seatMap=(TextView) view.findViewById(R.id.seatMap);

        System.out.println(jsonObject.toString());
            if(jsonObject.has("artists")) {
                artist.setText(jsonObject.getString("artists"));
            }else{
                TextView artistLabel=(TextView) view.findViewById(R.id.artistLabel);
                artist.setVisibility(View.GONE);
                artistLabel.setVisibility(View.GONE);
            }
            if(jsonObject.has("venue")) {
                venue.setText(jsonObject.getString("venue"));
            }else{
                TextView venueLabel=(TextView) view.findViewById(R.id.venueLabel);
                venue.setVisibility(View.GONE);
                venueLabel.setVisibility(View.GONE);
            }
            if(jsonObject.has("time")) {
                date.setText(jsonObject.getString("time"));
            }else{
                TextView dateLabel=(TextView) view.findViewById(R.id.dateLabel);
                date.setVisibility(View.GONE);
                dateLabel.setVisibility(View.GONE);
            }
            if(jsonObject.has("category")) {
                category.setText(jsonObject.getString("category"));
            }else{
                TextView categoryLabel=(TextView) view.findViewById(R.id.categoryLabel);
                category.setVisibility(View.GONE);
                categoryLabel.setVisibility(View.GONE);
            }
            if(jsonObject.has("priceRange")) {
                priceRange.setText(jsonObject.getString("priceRange"));
            }else{
                TextView priceRangeLabel=(TextView) view.findViewById(R.id.priceRangeLabel);
                priceRange.setVisibility(View.GONE);
                priceRangeLabel.setVisibility(View.GONE);
            }
            if(jsonObject.has("ticketStatus")) {
                ticketStatus.setText(jsonObject.getString("ticketStatus"));
            }else{
                TextView ticketStatusLabel=(TextView) view.findViewById(R.id.ticketStatusLabel);
                ticketStatus.setVisibility(View.GONE);
                ticketStatusLabel.setVisibility(View.GONE);
            }
            if(jsonObject.has("buyTicketAt")) {
                buyTicketAt.setText(Html.fromHtml(String.format("<a href=\"%s\">Ticketmaster</a> ", jsonObject.getString("buyTicketAt")),1));
                buyTicketAt.setMovementMethod(LinkMovementMethod.getInstance());
            }else{
                TextView buyTicketAtLabel=(TextView) view.findViewById(R.id.buyTicketAtLabel);
                buyTicketAt.setVisibility(View.GONE);
                buyTicketAtLabel.setVisibility(View.GONE);
            }
            if(jsonObject.has("seatMap")) {
                seatMap.setText(Html.fromHtml(String.format("<a href=\"%s\">View Seat Map Here</a> ", jsonObject.getString("seatMap")),1));
                seatMap.setMovementMethod(LinkMovementMethod.getInstance());

            }else{
                TextView seatMapLabel=(TextView) view.findViewById(R.id.seatMapLabel);
                seatMap.setVisibility(View.GONE);
                seatMapLabel.setVisibility(View.GONE);
            }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_info, container, false);
        try {
            init(root);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
}