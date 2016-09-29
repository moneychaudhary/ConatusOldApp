package com.example.money.conatusapp.TeamMembers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.money.conatusapp.R;

import java.util.ArrayList;
import java.util.List;

import Tabs.SlidingTabLayout;


public class TeamFragment extends Fragment {

    private SlidingTabLayout mTabs;
    private ViewPager mViewPager;


    public TeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_fragment, container, false);
        List<Fragment> list = new ArrayList<>();
        MentorsFragment mentorsFragment = new MentorsFragment();
        list.add(mentorsFragment);
        CurrentTeamFragment currentTeamFragment = new CurrentTeamFragment();
        list.add(currentTeamFragment);
        mTabs = (SlidingTabLayout) view.findViewById(R.id.tabs_team_fragment);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpage_team_fragment);
        mViewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), list, getActivity()));
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mViewPager);
        return view;
    }

}
