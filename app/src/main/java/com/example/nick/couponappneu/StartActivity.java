package com.example.nick.couponappneu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }

public void gotoBK(View view){
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtra("RESTAURANT_PREFIX", "burger_king");
    startActivity(intent);
}


    public void gotoMCD(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RESTAURANT_PREFIX", "mc_donalds");
        startActivity(intent);
    }


    public void gotoKFC(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RESTAURANT_PREFIX", "kfc");
        startActivity(intent);
    }


    public void gotoSubway(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RESTAURANT_PREFIX", "subway");
        startActivity(intent);
    }


    public void gotoNordsee(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("RESTAURANT_PREFIX", "nordsee");
        startActivity(intent);
    }

}


