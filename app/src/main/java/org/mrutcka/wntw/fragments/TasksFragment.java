package org.mrutcka.wntw.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

import org.mrutcka.wntw.AddNewTask;
import org.mrutcka.wntw.LoginActivity;
import org.mrutcka.wntw.R;
import org.mrutcka.wntw.adapters.ViewPagerAdapter;

import java.util.Objects;


public class TasksFragment extends Fragment {

    private String mParam1;
    private String mParam2;

    private String email, password, userID, projectID, projectName;


    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString("userID", param1);
        args.putString("projectID", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("userID");
            mParam2 = getArguments().getString("projectID");
        }
    }

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle result = this.getArguments();
        userID = result.getString("userID");
        projectID = result.getString("projectID");

        View inflatedView = inflater.inflate(R.layout.fragment_tasks, container, false);

        tabLayout = inflatedView.findViewById(R.id.tabLayout);
        viewPager2 = inflatedView.findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), projectID, userID);
        viewPager2.setAdapter(viewPagerAdapter);
        frameLayout = inflatedView.findViewById(R.id.task_frame_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                        tabLayout.getTabAt(position).select();
                }
                super.onPageSelected(position);
            }
        });

        return inflatedView;
    }
}