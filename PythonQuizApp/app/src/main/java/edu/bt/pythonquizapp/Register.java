package edu.bt.pythonquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress;
    EditText registerPassword;
    EditText confirm_Password;
    Button btnRegister;
    Button goToLogin;
    ProgressBar nprogressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        registerPassword = findViewById(R.id.registerpassword);
        confirm_Password = findViewById(R.id.confirm_Password);
        btnRegister = findViewById(R.id.btnregister);
        goToLogin = findViewById(R.id.goToLogin);
        nprogressBar = findViewById(R.id.nprogressBar);

        fAuth = FirebaseAuth.getInstance();
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extract the data from the form.....
                String fullName = editTextTextPersonName.getText().toString();
                String email = editTextTextEmailAddress.getText().toString();
                String password = registerPassword.getText().toString();
                String confirmPassword = confirm_Password.getText().toString();

                if (fullName.isEmpty()) {
                    editTextTextPersonName.setError("Full Name is required");
                    return;
                }
                if (email.isEmpty()) {
                    editTextTextEmailAddress.setError("Email is required");
                    return;
                }
                if (password.isEmpty()) {
                    registerPassword.setError("Password field cannot be empty");
                    return;
                }
                if (password.length() < 6) {
                    registerPassword.setError("your password should be of 6 character or more");
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    confirm_Password.setError("Confirm Password field cannot be empty");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    registerPassword.setError("Password does not match,Type again!");
                    return;
                }
                //data is validated by then
                Toast.makeText(Register.this, "Data is validated", Toast.LENGTH_SHORT).show();
                nprogressBar.setVisibility(View.VISIBLE);


                fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Sending the user to the Dashboard or home
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();  ///once user finished doing login user cannot come to register field again
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}