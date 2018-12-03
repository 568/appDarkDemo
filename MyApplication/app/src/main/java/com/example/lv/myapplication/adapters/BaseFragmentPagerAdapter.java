package com.example.lv.myapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.example.lv.myapplication.ui.BaseLazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 18-11-30.
 * 1.少量的fragment的适配器（FragmentPagerAdapter会缓存）
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseLazyFragment> mFragments;
    private FragmentManager fragmentManager;
    private List<String> tags;
    String[] pageTitles;
    public BaseFragmentPagerAdapter(FragmentManager fm, List<BaseLazyFragment> fragments,String[] titles) {
        super(fm);
        this.tags = new ArrayList<>();
        this.fragmentManager = fm;
        this.mFragments = fragments;
        this.pageTitles=titles;
    }

    public void setNewFragments(List<BaseLazyFragment> fragments) {
        if (this.tags != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < tags.size(); i++) {
                fragmentTransaction.remove(fragmentManager.findFragmentByTag(tags.get(i)));
            }
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
            tags.clear();
        }
        this.mFragments = fragments;
        notifyDataSetChanged();
    }

    public List<BaseLazyFragment> getFragments() {
        return mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        tags.add(makeFragmentName(container.getId(), getItemId(position)));
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mFragments.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
