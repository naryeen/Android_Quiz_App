package edu.bt.pythonquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
    EditText userPassword, userConfPassword;
    Button btn_savePassword;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        userPassword = findViewById(R.id.userPassword);
        userConfPassword = findViewById(R.id.userConfPassword);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btn_savePassword = findViewById(R.id.btn_savePassword);
        btn_savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPassword.getText().toString().isEmpty()){
                    userPassword.setError("Required Field");
                    return;
                }
                if (userConfPassword.getText().toString().isEmpty()){
                    userConfPassword.setError("Required Field");
                    return;
                }
                if(!userPassword.getText().toString().equals(userConfPassword.getText().toString())){
                    userConfPassword.setError("Password Do not Match");
                    return;
                }
                firebaseUser.updatePassword(userPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ResetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


}