package com.example.myapplication.ui.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getCount() {
        return 6;
    }

    public CharSequence getPageTitle(int position){
        String title = "";
        switch (position){
            case 0:
                title = "About";
                break;
            case 1:
                title = "Stats";
                break;
            case 2:
                title = "Camera Roll";
                break;
            case 3:
                title = "Public";
                break;
            case 4:
                title = "Albums";
                break;
            case 5:
                title = "Groups";
                break;
        }
        return title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new StatsFragment();
            case 2:
                return new CameraRollFragment();
            case 3:
                return new PublicFragment();
            case 4:
                return new AlbumsFragment();
            case 5:
                return new GroupsFragment();
            default:
                return new AboutFragment();
        }
    }
}
