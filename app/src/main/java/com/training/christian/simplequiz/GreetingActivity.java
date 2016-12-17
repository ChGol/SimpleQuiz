package com.training.christian.simplequiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GreetingActivity extends AppCompatActivity {

    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        //1. Odczytanie przekazanego parametru
        String name = getIntent().getStringExtra("name");
        //2. Wyświetlenie go na kontrolce TextView
        mTextView = (TextView) findViewById(R.id.greeting);
        mTextView.setText(name);

    }
}
