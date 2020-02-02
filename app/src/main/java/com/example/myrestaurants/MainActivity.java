package com.example.myrestaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();
   // private Button mFindRestaurantButton;
    //private EditText mLocationEditText;
    @BindView(R.id.button) Button mFindRestaurantButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;
   // @BindView(R.id.textView) TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mFindRestaurantButton = (Button)findViewById(R.id.button);
//        mLocationEditText =(EditText)findViewById(R.id.locationEditText);
        ButterKnife.bind(this);
        // added
        mFindRestaurantButton.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            //do something
//                Toast.makeText(MainActivity.this,"Ah Taniua",Toast.LENGTH_LONG).show();
            String location = mLocationEditText.getText().toString();

            Intent intent = new Intent(MainActivity.this, RestaurantsActivity.class);
            intent.putExtra("location",location);
            startActivity(intent);
//                String location = mLocationEditText.getText().toString();
//                Toast.makeText(MainActivity.this, location, Toast.LENGTH_LONG).show();
        }
    }

