package edu.uta.edu.uta.utils;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    int patientId;
    public PageAdapter(FragmentManager fm, int NumOfTabs,int patientId) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.patientId = patientId;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("patient_id",patientId);

        switch (position) {
            case 0:
                Fragment tab1 = new TabFragment1();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                Fragment tab2 = new TabFragment2();
                tab2.setArguments(bundle);
                return tab2;
            /*case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;
            */
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}