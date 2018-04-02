package hacker.l.coldstore.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> stringList = new ArrayList<>();

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void addFragment(Fragment fragment, String s) {
        fragmentList.add(fragment);
        stringList.add(s);
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }

}
