package edu.bt.pythonquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public TextView title;
    public Button start;

    Button btn_logout;
    //    Button btn_verifyEmail;
//    TextView verifyMEssage,
    TextView reset_password;
    FirebaseAuth auth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout = findViewById(R.id.btn_logout);
//        verifyMEssage = findViewById(R.id.verifyMEssage);
//        btn_verifyEmail = findViewById(R.id.btn_verifyEmail);
        reset_password = findViewById(R.id.reset_password);

        title = findViewById(R.id.main_title);
        start = findViewById(R.id.btn_main_start);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        title.setTypeface(typeface);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });


        auth = FirebaseAuth.getInstance();

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

//        if (!auth.getCurrentUser().isEmailVerified()) {
//            btn_verifyEmail.setVisibility(View.VISIBLE);
//            verifyMEssage.setVisibility(View.VISIBLE);
//        }
//        btn_verifyEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(MainActivity.this, "Verification Email sent.", Toast.LENGTH_SHORT).show();
//                        btn_verifyEmail.setVisibility(View.GONE);
//                        verifyMEssage.setVisibility(View.GONE);
//                    }
//                });
//            }
//        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.reset_password) {
            startActivity(new Intent(getApplicationContext(), ResetPassword.class));

        }

        if (item.getItemId() == R.id.reset_password) {
            startActivity(new Intent(getApplicationContext(), ResetPassword.class));
        }
        if (item.getItemId() == R.id.update_email) {
            View view = inflater.inflate(R.layout.reset_pop, null);
            reset_alert.setTitle("Update Email?").setMessage("Enter Your New Email Address.")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Validates the email address
                            //from the link created
                            EditText email = view.findViewById(R.id.reset_email_pop);
                            if (email.getText().toString().isEmpty()) {
                                email.setError("Required Field");
                            }
                            //send the reset the link
                            FirebaseUser user = auth.getCurrentUser();
                            user.updateEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "Email updated successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }).setNegativeButton("Cancel", null)
                    .setView(view)
                    .create().show();
        }
        if (item.getItemId() == R.id.delete_account) {
            reset_alert.setTitle("Delete Account Permanently").setMessage("Are you sure??").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseUser user = auth.getCurrentUser();
                    user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MainActivity.this, "Account Deleted Successfully", Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).setNegativeButton("Cancel", null).create().show();
        }
        return super.onOptionsItemSelected(item);
    }


}