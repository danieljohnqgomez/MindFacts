package com.danielgomez.mindfacts.adapters;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.danielgomez.mindfacts.fragments.FavoritesFragment;
import com.danielgomez.mindfacts.fragments.FeedFragment;

public class MainTabsAdapter extends FragmentPagerAdapter {

    public MainTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new FeedFragment();
            case 1:
                return new FavoritesFragment();
            case 2:
                return new FavoritesFragment();
            case 3:
                return new FavoritesFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 4;
    }
}