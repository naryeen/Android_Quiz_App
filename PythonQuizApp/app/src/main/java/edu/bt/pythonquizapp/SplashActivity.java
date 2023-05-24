package edu.bt.pythonquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {
    private TextView textViewSplash;
    private ImageView imageViewSplash;
    public static List<CategoryModel> catList = new ArrayList<>();
    public static int sellected_category_index = 0;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textViewSplash = findViewById(R.id.textViewSplash);
        imageViewSplash = findViewById(R.id.imageViewSplash);

//        Intent intent = new Intent(SplashActivity.this, Login.class);
//        startActivity(intent);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        textViewSplash.setTypeface(typeface);
        Animation ani = AnimationUtils.loadAnimation(this, R.anim.myanimation);
        textViewSplash.setAnimation(ani);

        firestore = FirebaseFirestore.getInstance();

        //calling the thread and fetching the data and immediately directing to main activity, need to  change the fetvhing style
        //calling methods , main methods
        new Thread(() -> {

            //we dont need time to sleep because we want to fetch the data from the server that takes time , so simply calling methids
            //sleep(3000);
            loadData();


        }).start();


    }

    private void loadData() {
        //clear Lessons or category list
        catList.clear();
        //directly connects to firebase , with collections in firebase
        firestore.collection("QUIZ").document("Categories")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            //fetch the lesson list here
                            //how many lessons are there
                            long count = (long) doc.get("COUNT");
                            for (int i = 1; i <= count; i++) {
                                String catName = doc.getString("CAT" + String.valueOf(i) + "_NAME");
                                String catID = doc.getString("CAT" + String.valueOf(i) + "_ID");
                                catList.add(new CategoryModel(catID,catName));
                            }
                                //After fetching thee lessons  then only directing to main activity
                            Intent intent = new Intent(SplashActivity.this, Login.class);
                            startActivity(intent);

                        } else {
                            //no no document on the server
                            Toast.makeText(SplashActivity.this, "No Lesson Document Exists", Toast.LENGTH_SHORT).show();
                            //TODO neww changes
                        }
                        //finish();
                        SplashActivity.this.finish();
                    } else {
                        Toast.makeText(SplashActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



    }
}