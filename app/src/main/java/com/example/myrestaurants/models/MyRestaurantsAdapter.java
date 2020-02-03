package com.example.myrestaurants.models;

import android.content.Context;
import android.widget.ArrayAdapter;


public class MyRestaurantsAdapter extends ArrayAdapter {
    private String[] mRestaurants;
    private String[] mCuisines;
    private Context mContext;

    public MyRestaurantsAdapter( Context mContext, int resource , String[] mRestaurants, String[] mCuisines) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mRestaurants = mRestaurants;
        this.mCuisines = mCuisines;
    }

    @Override
    public Object getItem(int position) {
        String restaurant = mRestaurants[position];
        String cuisine = mCuisines[position];
        return String.format("%s \nServes great: %s", restaurant, cuisine);
    }

    @Override
    public int getCount() {
        return mRestaurants.length;
    }
}
