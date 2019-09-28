package com.example.kattyappv22;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.app.ProgressDialog;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

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
    private EditText TextMail;
    private EditText TextPassword;
    private Button Ingresar;
    private ProgressDialog progressDialog;

    //Creo un objeto para el registro por mail y el password
    private FirebaseAuth firebaseBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instancia para la Autenticacion
        firebaseBase = FirebaseAuth.getInstance();



        //Asignar variables para el Registro
        TextMail = findViewById(R.id.txt_email);
        TextPassword = findViewById(R.id.txt_password);
        Ingresar = findViewById(R.id.btn_ingresar);
        progressDialog = new ProgressDialog(this);


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



        //Aca empiezan los listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                // Si el logeo fue exitoso, nos manda al otro activity
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }
        });

        Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //llama al metodo ingresar cuando presiona el boton
                ingresar();
            }
        });

    }

    //Aca empiezan los metodos
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

    //Manda al activity registrar
    public void registrarse(View view) {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        startActivity(new Intent(MainActivity.this, act_registrarse.class));
    }

    private void ingresar(){
        //Aca se obtiene lo que hay en las cajas de texto
        String email = TextMail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Si estan vacias manda estos errores
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Debes ingresar un correo valido", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debes ingresar una contraseña valida", Toast.LENGTH_LONG).show();
            return;
        }

        //Si los datos son correctos
        progressDialog.setMessage("Realizando el registro...");
        progressDialog.show();

        //Funcion para logearse
        firebaseBase.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Sesion Exitosa!!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, act_menu.class));

                        } else {
                            //Si el inicio falla, muestra estos datos
                            if (task.getException()instanceof FirebaseAuthUserCollisionException){
                                //Toast.makeText(MainActivity.this, "Este usuario ya existe!", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Datos Incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}