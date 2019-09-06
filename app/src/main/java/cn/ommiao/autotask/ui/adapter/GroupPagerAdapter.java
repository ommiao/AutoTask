package cn.ommiao.autotask.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import cn.ommiao.autotask.ui.main.GroupFragment;

public class GroupPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<GroupFragment> fragments;

    public GroupPagerAdapter(FragmentManager fm, ArrayList<GroupFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

}
