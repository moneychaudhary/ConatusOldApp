package com.example.money.conatusapp.TeamMembers;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.money.conatusapp.R;
import com.example.money.conatusapp.TeamMembers.currentTeam.CurrentTeamFragment;

import java.util.ArrayList;
import java.util.List;

import Tabs.SlidingTabLayout;

public class TeamFragment extends Fragment {

    private SlidingTabLayout mTabs;
    private ViewPager mViewPager;
    private List<Fragment> mList;
    private ViewPagerAdapter mViewPagerAdapter;
    private View view;
    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        MentorsFragment mentorsFragment = new MentorsFragment();
        mList.add(mentorsFragment);
        CurrentTeamFragment currentTeamFragment = new CurrentTeamFragment();
        mList.add(currentTeamFragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.team_fragment, container, false);
        mTabs = (SlidingTabLayout) view.findViewById(R.id.tabs_team_fragment);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpage_team_fragment);
        if (mViewPagerAdapter == null) {
            mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mList, getContext());
        }
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mViewPager);
        return view;
    }



}
