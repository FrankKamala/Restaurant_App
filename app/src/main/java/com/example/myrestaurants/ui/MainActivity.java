package com.example.myrestaurants.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurants.R;
import com.example.myrestaurants.models.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();

    //    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;
    private ValueEventListener mSearchedLocationReferenceListener;

    private DatabaseReference mSearchedLocationReference;

    @BindView(R.id.button) Button mFindRestaurantButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
   // @BindView(R.id.textView) TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedLocationReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);
//        mFindRestaurantButton = (Button)findViewById(R.id.button);
//        mLocationEditText =(EditText)findViewById(R.id.locationEditText);
       // mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //mEditor = mSharedPreferences.edit();

        // added
        mSearchedLocationReferenceListener = mSearchedLocationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location: " + location); //log
                }

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {


            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindRestaurantButton.setOnClickListener(this);

    }

        @Override
        public void onClick(View v) {

            if(v == mFindRestaurantButton) {
                String location = mLocationEditText.getText().toString();

                saveLocationToFirebase(location);

//            if(!(location).equals("")) {
//                addToSharedPreferences(location);
//            }

                Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
                intent.putExtra("location", location);
                startActivity(intent);
            }

        }


    public void saveLocationToFirebase(String location) {
        mSearchedLocationReference.push().setValue(location);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedLocationReference.removeEventListener(mSearchedLocationReferenceListener);
    }


    //  private void addToSharedPreferences(String location) {
      //  mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
   // }

    }

