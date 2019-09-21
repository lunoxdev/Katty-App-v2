package com.example.kattyappv22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class act_registrarse extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_registrarse);

        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void atras2(View view) {

        progressBar.setVisibility(ProgressBar.VISIBLE);
        startActivity(new Intent(act_registrarse.this, MainActivity.class));
    }
}
