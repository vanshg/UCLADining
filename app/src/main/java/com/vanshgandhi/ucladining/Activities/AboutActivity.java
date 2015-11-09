package com.vanshgandhi.ucladining.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vanshgandhi.ucladining.R;

public class AboutActivity extends AppCompatActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.about));
        setSupportActionBar(toolbar);
        

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    
}
