package edu.bt.pythonquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class
Login extends AppCompatActivity {

    Button createAccount, btn_login, btn_forgot_password;
    EditText loginEmail, loginPassword;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    ProgressBar progressLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createAccount = findViewById(R.id.btn_createAccount);
        progressLogin = findViewById(R.id.progressLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        btn_login = findViewById(R.id.btn_login);
//        btn_forgot_password = findViewById(R.id.btn_forgot_password);

//login button
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the data and validate the data
                if (loginEmail.getText().toString().isEmpty()) {
                    loginEmail.setError("Email is Missing!");
                    return;
                }
                if (loginPassword.getText().toString().isEmpty()) {
                    loginPassword.setError("Password Missing!");
                    return; //now the data is validated;
                }

                //Login the user
                firebaseAuth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString())
                        .addOnSuccessListener(authResult -> {
                            //if the login is successful the user will send to the home.
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                progressLogin.setVisibility(View.VISIBLE);
            }
        });
    }
    //if the user have already registered then user can do login directly and
    //if the user have already login then user will be directly redirected to home or dashboard.
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}