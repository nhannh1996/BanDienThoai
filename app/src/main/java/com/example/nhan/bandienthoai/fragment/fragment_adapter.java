package com.example.nhan.bandienthoai.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Nhan on 4/3/2017.
 */

public class fragment_adapter extends FragmentStatePagerAdapter {
    public fragment_adapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag=new fragment_iphone();
                break;
            case 1:
                frag=new fragment_nokia();
                break;
            case 2:
                frag=new fragment_ss();
                break;
            case 3:
                frag=new fragment_oppo();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="IPHONE";
                break;
            case 1:
                title="NOKIA";
                break;
            case 2:
                title="SAMSUNG";
                break;
            case 3:
                title="OPPO";
                break;
        }

        return title;
    }
}
