package com.example.kattyappv22;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    //Variables

    int RC_SIGN_IN = 0;
    //Variable para el Sign In
    Button signInButton;
    //Variable para poder usar el autenticador de Firebase
    GoogleSignInClient mGoogleSignInClient;
    //Variable para el progressBar
    ProgressBar progressBar;
    //Variables para el Registro Authenticado por email y password
    //private EditText TextMail;
    //private EditText TextPassword;
    //private Button Ingresar;
    //private ProgressDialog progressDialog;

    //Autenticacion para el registro por mail y el password
    //private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instancia para la Autenticacion
        //firebaseAuth = FirebaseAuth.getInstance();


        //Asignar variables para el Registro
        //TextMail = findViewById(R.id.txt_email);
        //TextPassword = findViewById(R.id.txt_password);
        //Ingresar = findViewById(R.id.btn_ingresar);


        //asignar variables
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        //Initializing Views
        signInButton = findViewById(R.id.btn_google);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                // Si el logeo fue exitoso, nos manda al otro activity
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(MainActivity.this, act_menu.class));
            Toast.makeText(MainActivity.this,"Inicio de session Exitoso!!", Toast.LENGTH_SHORT).show();

        } catch (ApiException e) {

            progressBar.setVisibility(ProgressBar.INVISIBLE);
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(MainActivity.this, "Falló el inicio de sesion", Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para pasar al activity forgot password si da click en olvido de contraseña
    public void respassword(View view) {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        startActivity(new Intent(MainActivity.this, act_forgpassword.class));
    }

    public void registrarse(View view) {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        startActivity(new Intent(MainActivity.this, act_registrarse.class));
    }
}