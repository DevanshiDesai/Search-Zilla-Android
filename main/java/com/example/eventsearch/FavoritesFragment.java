package com.example.eventsearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import static android.view.View.GONE;
import static com.example.eventsearch.EventSummaryList.isFav;
import static com.example.eventsearch.MainActivity.sharedPreference;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public  ArrayList<String> title=new ArrayList<>();
    public  ArrayList<String> subTitle=new ArrayList<>();
    public  ArrayList<String> date=new ArrayList<>();
    public  ArrayList<String> id=new ArrayList<>();
    public  ArrayList<String> segmentId=new ArrayList<>();
    public  ArrayList<Boolean> favorites=new ArrayList<>();
    public ListViewAdapter favAdapter;
    Gson gson;

    public FavoritesFragment() {
        // Required empty public constructor
        getFav();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
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

//    public void notifyDataChanged(){
//        favAdapter.notifyDataSetChanged();
//    }


    public void getFav(){
        Gson gson = new Gson();
        Map<String, ?> json = sharedPreference.getAll();
        title.clear();
        subTitle.clear();
        date.clear();
        segmentId.clear();
        id.clear();
        favorites.clear();
        for (Map.Entry<String,?> entry : json.entrySet()){
            FavoritesPOJO fav = gson.fromJson((String) entry.getValue(), FavoritesPOJO.class);
            title.add(fav.getTitle());
            subTitle.add(fav.getSubTitle());
            date.add(fav.getDate());
            segmentId.add(fav.getSegmentId());
            id.add(fav.getId());
            favorites.add(isFav(fav.getId()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_favorites, container, false);

        RecyclerView recyclerView= root.findViewById(R.id.favRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        favAdapter =new ListViewAdapter(getContext(),title,subTitle,date,id,segmentId,favorites,"fav");
        recyclerView.setAdapter(favAdapter);
        return root;
    }
}