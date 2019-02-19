//package com.example.laptop_acer.firebaseapp.adapters;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//
//import com.example.laptop_acer.firebaseapp.fragments.HomeFragment;
//import com.example.laptop_acer.firebaseapp.fragments.DescriptionFragment;
//import com.example.laptop_acer.firebaseapp.fragments.AccountFragment;
//
//public class PagerAdapter extends FragmentStatePagerAdapter {
//
//    private int numberOfTabs;
//
//    public TownsPagerAdapter(FragmentManager fm, int numberOfTabs) {
//        super(fm);
//        this.numberOfTabs = numberOfTabs;
//    }
//
//
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                HomeFragment homeFragment = new HomeFragment();
//                return homeFragment;
//            case 1:
//                DescriptionFragment descriptionFragment = new DescriptionFragment();
//                return descriptionFragment;
//            case 2:
//                AccountFragment accountFragment = new AccountFragment();
//                return accountFragment;
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return numberOfTabs;
//    }
//
//
//
//}
