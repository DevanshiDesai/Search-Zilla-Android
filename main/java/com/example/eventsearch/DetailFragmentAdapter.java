package com.example.eventsearch;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class DetailFragmentAdapter extends FragmentStateAdapter {
    Bundle b;
    public DetailFragmentAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle,Bundle bundle) {
        super(fragmentManager, lifecycle);
        this.b=bundle;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                ArtistFragment a=new ArtistFragment();
                a.setArguments(b);
                return a;
            case 2:
                VenueFragment v=new VenueFragment();
                v.setArguments(b);
                return v;
        }
        InfoFragment i=new InfoFragment();
        i.setArguments(b);
        return i;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
