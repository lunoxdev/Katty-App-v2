package com.example.kattyappv22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class act_forgpassword extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_forgpassword);

        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void atras(View view) {

        progressBar.setVisibility(ProgressBar.VISIBLE);
        startActivity(new Intent(act_forgpassword.this, MainActivity.class));
    }
}
