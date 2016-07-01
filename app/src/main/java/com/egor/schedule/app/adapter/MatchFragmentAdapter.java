package com.egor.schedule.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Egor on 22.06.2016.
 */

public class MatchFragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> matchesList = new ArrayList<Fragment>();

    public MatchFragmentAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return matchesList.get(position);
    }

    @Override
    public int getCount() {
        return matchesList.size();
    }

    public void addFragment(Fragment fragment) {
        matchesList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "GAME " + position;
    }
}

