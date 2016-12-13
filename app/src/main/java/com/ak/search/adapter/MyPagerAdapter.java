package com.ak.search.adapter;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.ak.search.fragment.QuestionFragment;
import com.ak.search.model.Questions;

import java.util.List;

/**
 * Created by dg hdghfd on 08-12-2016.
 */

public class MyPagerAdapter  extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;


    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {

        super(fm);

        this.fragments = fragments;

    }

    @Override

    public Fragment getItem(int position) {

        return this.fragments.get(position);

    }


    @Override

    public int getCount() {

        return this.fragments.size();

    }

}
