package com.example.eventsearch;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private JSONObject jsonObject=null;
    public ArtistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistFragment newInstance(String param1, String param2) {
        ArtistFragment fragment = new ArtistFragment();
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
            try {
                jsonObject= new JSONObject(getArguments().getString("data"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.fragment_artist, container, false);
        try {
            init(root);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }







    @SuppressLint("ResourceAsColor")
    public void init(View view) throws JSONException {
        JSONArray jarray = jsonObject.getJSONArray("artistArray");
        TableLayout tl = (TableLayout)view.findViewById(R.id.artistTableLayout);
        if(jarray.length()<1){
            TextView t=(TextView) getView().findViewById(R.id.noRecs);
            t.setVisibility(View.VISIBLE);
            ScrollView s=(ScrollView) getView().findViewById(R.id.scrollView);
            s.setVisibility(View.GONE);
            return;
        }
        for (int i = 0; i < jarray.length(); i++)
        {
            if(!jarray.getJSONObject(i).isNull("data") && jarray.getJSONObject(i).getJSONObject("data").length()>0){
                if(jarray.getJSONObject(i).getJSONObject("data").has("name")){
                    TableRow tr = new TableRow(getActivity());
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                    TextView artistLabel = new TextView(getActivity());
                    artistLabel.setText("Artist Name");
                    artistLabel.setHeight(150);
                    artistLabel.setWidth(400);
                    artistLabel.setTypeface(artistLabel.getTypeface(), Typeface.BOLD);
                    //artistLabel.setLayoutParams(new RelativeLayout.LayoutParams(150,50));
                    tr.addView(artistLabel);

                    TextView artist = new TextView(getActivity());
                    artist.setText(jarray.getJSONObject(i).getJSONObject("data").getString("name"));
                    tr.addView(artist);
                    tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                }
                if(jarray.getJSONObject(i).getJSONObject("data").has("popularity")){
                    TableRow tr1 = new TableRow(getActivity());
                    tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView popularityLabel = new TextView(getActivity());
                    popularityLabel.setText("Popularity");
                    popularityLabel.setTypeface(popularityLabel.getTypeface(), Typeface.BOLD);
                    popularityLabel.setHeight(150);
                    popularityLabel.setWidth(400);
                    tr1.addView(popularityLabel);

                    TextView popularity = new TextView(getActivity());
                    popularity.setText(jarray.getJSONObject(i).getJSONObject("data").getString("popularity"));
                    tr1.addView(popularity);
                    tl.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                }
                if(jarray.getJSONObject(i).getJSONObject("data").has("followers")){
                    TableRow tr2 = new TableRow(getActivity());
                    tr2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView followersLabel = new TextView(getActivity());
                    followersLabel.setText("Followers");
                    followersLabel.setHeight(150);
                    followersLabel.setWidth(400);
                    followersLabel.setTypeface(followersLabel.getTypeface(), Typeface.BOLD);
                    tr2.addView(followersLabel);

                    TextView followers = new TextView(getActivity());
                    followers.setText(jarray.getJSONObject(i).getJSONObject("data").getString("followers"));

                    tr2.addView(followers);
                    tl.addView(tr2, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                }
                if(jarray.getJSONObject(i).getJSONObject("data").has("checkAt")){
                    TableRow tr3 = new TableRow(getActivity());
                    tr3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    TextView checkAtLabel = new TextView(getActivity());
                    checkAtLabel.setText("Check At");
                    checkAtLabel.setHeight(250);
                    checkAtLabel.setWidth(400);
                    checkAtLabel.setTypeface(checkAtLabel.getTypeface(), Typeface.BOLD);
                    tr3.addView(checkAtLabel);



                    TextView checkAt = new TextView(getActivity());
                    checkAt.setText(Html.fromHtml(String.format("<a href=\"%s\">Spotify</a> ", jarray.getJSONObject(i).getJSONObject("data").getString("checkAt")),1));
                    checkAt.setLinkTextColor(ContextCompat.getColor(getContext(), R.color.purple_700));
                    checkAt.setMovementMethod(LinkMovementMethod.getInstance());

                    tr3.addView(checkAt);
                    tl.addView(tr3, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                }

            }else{
                if(jarray.getJSONObject(i).has("keyword")){

                    TableRow tr = new TableRow(getActivity());
//                    TableRow.LayoutParams bLp = (TableRow.LayoutParams) b.getLayoutParams();
//                    bLp.span = 2;
//                    b.setLayoutParams(bLp);


                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                    TextView artistLabel = new TextView(getActivity());
                    artistLabel.setText(jarray.getJSONObject(i).getString("keyword")+" : No details.");
                    artistLabel.setTypeface(artistLabel.getTypeface(), Typeface.BOLD);
                    artistLabel.setHeight(200);
                    artistLabel.setWidth(1000);
//                    artistLabel.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
                    tr.addView(artistLabel);
                    tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                }
            }


        }
    }

















}