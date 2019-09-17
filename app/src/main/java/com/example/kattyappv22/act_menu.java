package com.example.kattyappv22;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class act_menu extends AppCompatActivity {

    Button sign_out;


    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_menu);

        //asignar variables
        progressBar = findViewById(R.id.progress_circular1);

        sign_out = findViewById(R.id.btn_cerrarsesion);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                //startActivity(new Intent(act_menu.this,MainActivity.class));
                Intent i = new Intent(act_menu.this,MainActivity.class);
                startActivity(i);
                finish();
                progressBar.setVisibility(ProgressBar.VISIBLE);
                Toast.makeText(act_menu.this,"Cierre de sesion Exitoso!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
