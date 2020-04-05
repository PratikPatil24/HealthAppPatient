package com.pratik.healthapppatient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pratik.healthapppatient.historyfragments.PrescriptionsFragment;
import com.pratik.healthapppatient.historyfragments.ReportsFragment;
import com.pratik.healthapppatient.historyfragments.TreatmentsFragment;

public class HistoryPageController extends FragmentPagerAdapter {

    int tabcount;

    public HistoryPageController(@NonNull FragmentManager fm, int tabcount) {
        super(fm);
        this.tabcount = tabcount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PrescriptionsFragment();
            case 1:
                return new TreatmentsFragment();
            case 2:
                return new ReportsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
