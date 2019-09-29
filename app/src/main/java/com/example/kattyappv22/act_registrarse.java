package com.example.kattyappv22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class act_registrarse extends AppCompatActivity {

    //Asignacion de variables
    private EditText TextNombre;
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextConfirPassword;
    private EditText TextPhone;
    private Button Registrar;

    //Variable para el firebase
    private FirebaseAuth firebaseBase;

    private ProgressDialog progressDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_registrarse);

        firebaseBase = FirebaseAuth.getInstance();

        //Asignar referencias a las variables
        TextNombre = findViewById(R.id.txt_name);
        TextEmail = findViewById(R.id.txt_email);
        TextPassword = findViewById(R.id.txt_password);
        TextConfirPassword = findViewById(R.id.txt_confirmpassword);
        TextPhone = findViewById(R.id.txt_phone);
        Registrar = findViewById(R.id.btn_registrarse);
        progressDialog = new ProgressDialog(this);

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarse();
            }
        });


        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    public void atras(View view) {

        progressBar.setVisibility(ProgressBar.VISIBLE);
        startActivity(new Intent(act_registrarse.this, MainActivity.class));
    }

    public void registrarse() {
        //Aca se obtiene lo que hay en las cajas de texto
        String nombre = TextNombre.getText().toString();
        String email = TextEmail.getText().toString();
        String password = TextPassword.getText().toString().trim();
        String confirpassword = TextConfirPassword.getText().toString().trim();
        //int phone = Integer.parseInt(TextPhone.getText().toString());

        //Si estan vacias manda estos errores
        if (TextUtils.isEmpty(nombre)) {
           Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
           return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confirpassword)) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
            return;
        }
        //if (TextUtils.isEmpty(phone)) {
            //Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
            //return;
        //}
        if (!password.equals(confirpassword)) {
            Toast.makeText(this,"La contraseñas no coinciden",Toast.LENGTH_LONG).show();
            return;
        }


        //Si los datos son correctos
        progressDialog.setMessage("Validando, Por favor espere...");
        progressDialog.show();

        //Funcion para registrar
        firebaseBase.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(act_registrarse.this, "Registro Exitoso, Inicie Sesion!!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(act_registrarse.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(act_registrarse.this, "Datos Incorrectos", Toast.LENGTH_LONG).show();
                        }

                        progressDialog.dismiss();
                    }
                });
    }
}
