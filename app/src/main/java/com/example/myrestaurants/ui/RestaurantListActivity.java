package com.example.myrestaurants.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurants.R;
import com.example.myrestaurants.adapters.RestaurantListAdapter;
import com.example.myrestaurants.models.Business;
import com.example.myrestaurants.models.Constants;
import com.example.myrestaurants.models.YelpBusinessesSearchResponse;
import com.example.myrestaurants.network.YelpApi;
import com.example.myrestaurants.network.YelpClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantListActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;
   // private TextView mLocationView;
   // private ListView mListView;



   public static final String TAG = RestaurantListActivity.class.getSimpleName();
//   @BindView(R.id.locationTextView)TextView  mLocationView;
//   @BindView(R.id.listView)ListView mListView;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    private RestaurantListAdapter mAdapter;

    public List<Business> restaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        ButterKnife.bind(this); // bind here


//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,restaurants);

//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String restaurant = ((TextView)view).getText().toString();
//
//
//                Toast.makeText(RestaurantListActivity.this, restaurant, Toast.LENGTH_LONG).show();
//            }
//        });

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");

        //mLocationView.setText("Here are all the restaurants near: " + location);

        YelpApi client = YelpClient.getClient();

        Call<YelpBusinessesSearchResponse> call = client.getRestaurants(location, "restaurants");
        call.enqueue(new Callback<YelpBusinessesSearchResponse>() {
            @Override
            public void onResponse(Call<YelpBusinessesSearchResponse> call, Response<YelpBusinessesSearchResponse> response) {
                hideProgressBar();

                if (response.isSuccessful()) {
                    restaurants = response.body().getBusinesses();
                    mAdapter = new RestaurantListAdapter(RestaurantListActivity.this, restaurants);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(RestaurantListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
//                    List<Business> restaurantsList = response.body().getBusinesses();
//                    String[] restaurants = new String[restaurantsList.size()];
//                    String[] categories = new String[restaurantsList.size()];
//
//                    for (int i = 0; i < restaurants.length; i++){
//                        restaurants[i] = restaurantsList.get(i).getName();
//                    }
//
//                    for (int i = 0; i < categories.length; i++) {
//                        Category category = restaurantsList.get(i).getCategories().get(0);
//                        categories[i] = category.getTitle();
//                    }
//
//                    ArrayAdapter adapter
//                            = new MyRestaurantsAdapter(RestaurantListActivity.this, android.R.layout.simple_list_item_1, restaurants, categories);
//                    mListView.setAdapter(adapter);

                    showRestaurants();
                } else {
                    showUnsuccessfulMessage();
                }
            }

            @Override
            public void onFailure(Call<YelpBusinessesSearchResponse> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }

        });

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);




       // Log.d("Shared Pref Location", mRecentAddress);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showRestaurants() {
        mRecyclerView.setVisibility(View.VISIBLE);
//        mListView.setVisibility(View.VISIBLE);
//        mLocationView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}