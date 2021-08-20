package com.example.eventsearch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import static com.example.eventsearch.EventSummaryList.isFav;
import static com.example.eventsearch.MainActivity.editor;
import static com.example.eventsearch.MainActivity.sharedPreference;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder>{

    private ArrayList<String> mSegmentId=new ArrayList<>();
    private ArrayList<String> mTitles=new ArrayList<>();
    private ArrayList<String> mSubTitles=new ArrayList<>();
    private ArrayList<String> mDate=new ArrayList<>();
    private ArrayList<String> mId=new ArrayList<>();
    private ArrayList<Boolean> favorites=new ArrayList<>();
    private String callFrom="";

    Gson gson;

    private Context mContext;
    private final String TAGNAME="ListViewAdapter";

    public ListViewAdapter(Context mContext,ArrayList<String> mTitles,ArrayList<String> mSubTitles,ArrayList<String> mDate,ArrayList<String> mId,ArrayList<String> mSegmentId,ArrayList<Boolean> favorites,String callFrom) {
        this.mSegmentId = mSegmentId;
        this.mTitles = mTitles;
        this.mSubTitles = mSubTitles;
        this.mDate = mDate;
        this.mId = mId;
        this.favorites = favorites;
        this.mContext = mContext;
        this.callFrom = callFrom;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch(mSegmentId.get(position).toLowerCase()){
            case "music":
                Glide.with(mContext).asBitmap().load(R.drawable.music_icon).into(holder.image);
                break;
            case "arts & theatre":
                Glide.with(mContext).asBitmap().load(R.drawable.art_icon).into(holder.image);
                break;
            case "film":
                Glide.with(mContext).asBitmap().load(R.drawable.film_icon).into(holder.image);
                break;
            case "sports":
                Glide.with(mContext).asBitmap().load(R.drawable.ic_sport_icon).into(holder.image);
                break;
            default:
                Glide.with(mContext).asBitmap().load(R.drawable.miscellaneous_icon).into(holder.image);
                break;
        }

        holder.title.setText(mTitles.get(position));
        holder.subTitle.setText(mSubTitles.get(position));
        holder.date.setText(mDate.get(position));
        holder.id.setText(mId.get(position));
        holder.segmentId.setText(mSegmentId.get(position));
        if(favorites.get(position)){
            holder.toggleButton.setChecked(true);
            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.heart_fill_red));
        }else{
            holder.toggleButton.setChecked(false);
            holder.toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.heart_outline_black));
        }


        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i(TAGNAME,"onClick: clicked on: "+mTitles.get(position));
                Intent intent = new Intent(mContext, EventDetails.class);
                intent.putExtra("id", mId.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    public void getFav(){
        Gson gson = new Gson();
        Map<String, ?> json = sharedPreference.getAll();
        mTitles.clear();
        mSubTitles.clear();
        mDate.clear();
        mSegmentId.clear();
        mId.clear();
        favorites.clear();
        for (Map.Entry<String,?> entry : json.entrySet()){
            FavoritesPOJO fav = gson.fromJson((String) entry.getValue(), FavoritesPOJO.class);
            mTitles.add(fav.getTitle());
            mSubTitles.add(fav.getSubTitle());
            mDate.add(fav.getDate());
            mSegmentId.add(fav.getSegmentId());
            mId.add(fav.getId());
            favorites.add(isFav(fav.getId()));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        TextView subTitle;
        TextView date;
        TextView id;
        TextView segmentId;
        LinearLayout parentLayout;
        ToggleButton toggleButton;
        public ViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageView);
            title=itemView.findViewById(R.id.title);
            subTitle=itemView.findViewById(R.id.subtitle);
            date=itemView.findViewById(R.id.date);
            id=itemView.findViewById(R.id.id);
            segmentId=itemView.findViewById(R.id.segmentId);
            parentLayout=itemView.findViewById(R.id.parent_layout);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.myToggleButton);

            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!isChecked) {
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.heart_outline_black));
                        editor.remove(id.getText().toString());
                        editor.commit();
                        if (callFrom == "fav") {
                            getFav();
                        }
//                        getFav();
                    }else if(callFrom=="list") {
                        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.heart_fill_red));
                        FavoritesPOJO fav=new FavoritesPOJO(title.getText().toString(),subTitle.getText().toString(),date.getText().toString(),segmentId.getText().toString(),id.getText().toString());
                        gson = new Gson();
                        String json = gson.toJson(fav);
                        editor.putString(id.getText().toString(), json);
                        editor.commit();
                    }
                }
            });
        }
    }


}
























//
//public class ListViewAdapter extends ArrayAdapter<String> {
//    private final Activity context;
//    private final ArrayList<String> maintitle;
//    private final ArrayList<String> subtitle;
//    private final ArrayList<String>  date;
//    public ListViewAdapter(Activity context, ArrayList<String> maintitle,ArrayList<String> subtitle,
//                           ArrayList<String> date) {
//        super(context, R.layout.list_items, maintitle);
//        // TODO Auto-generated constructor stub
//        this.context=context;
//        this.maintitle=maintitle;
//        this.subtitle=subtitle;
//        this.date=date;
//    }
//    public View getView(int position,View view,ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.list_items, null,true);
//        TextView titleText = (TextView) rowView.findViewById(R.id.title);
//        TextView dateText = (TextView) rowView.findViewById(R.id.date);
//        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
//        titleText.setText(maintitle.get(position));
//        dateText.setText(date.get(position));
//        subtitleText.setText(subtitle.get(position));
//        return rowView;
//    };
//}