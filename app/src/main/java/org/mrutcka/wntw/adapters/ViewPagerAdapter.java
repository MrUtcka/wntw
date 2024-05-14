package org.mrutcka.wntw.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.mrutcka.wntw.R;
import org.mrutcka.wntw.fragments.DoFragment;
import org.mrutcka.wntw.fragments.DoingFragment;
import org.mrutcka.wntw.fragments.DoneFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    String projectID, userID;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String projectID, String userID) {
        super(fragmentActivity);
        this.projectID = projectID;
        this.userID = userID;
    }

    private Bundle tradeDATA() {
        Bundle bundle = new Bundle();
        bundle.putString("projectID", projectID);
        bundle.putString("userID", userID);

        return bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        DoFragment dofragment = new DoFragment();
        DoingFragment doingfragment = new DoingFragment();
        DoneFragment donefragment = new DoneFragment();
        dofragment.setArguments(tradeDATA());
        doingfragment.setArguments(tradeDATA());
        donefragment.setArguments(tradeDATA());

        switch (position) {
            case 0:
                return dofragment;
            case 1:
                return doingfragment;
            case 2:
                return donefragment;
            default:
                return dofragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
