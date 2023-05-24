package edu.bt.pythonquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static edu.bt.pythonquizapp.SplashActivity.catList;
import static edu.bt.pythonquizapp.SplashActivity.sellected_category_index;

public class SetsActivity extends AppCompatActivity {
     GridView sets_grid;
     private FirebaseFirestore firestore;
     private Dialog loadingDialog;

     public static List<String> setsIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

//        getSupportActionBar().setTitle("Lessons");
//        Toolbar toolbar = findViewById(R.id.sets_toolbar);
//        setSupportActionBar(toolbar);

//        String title = getIntent().getStringExtra("CATEGORY");
//        lesson_id = getIntent().getIntExtra("CATEGORY_ID",1);
        getSupportActionBar().setTitle(catList.get(sellected_category_index).getName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sets_grid =findViewById(R.id.sets_gridView);

        loadingDialog = new Dialog(SetsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        firestore = FirebaseFirestore.getInstance();
        loadSets();
        // this is for static only. adapter is created but only after getting data from server
//        SetsAdapter adapter = new SetsAdapter(6);
//        sets_grid.setAdapter(adapter);

    }



    public void loadSets(){

        setsIDs.clear();

        firestore.collection("QUIZ").document(catList.get(sellected_category_index).getId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                long noOfSets = (long) documentSnapshot.get("SETS");


                for (int i = 1; i <= noOfSets; i++) {
                    setsIDs.add(documentSnapshot.getString("SET" + String.valueOf(i) + "_ID"));

                }
                //fetching counter form firestore
//                catList.get(sellected_category_index).setSetCounter(documentSnapshot.getString("COUNTER"));
//                catList.get(sellected_category_index).setNoOfSets(String.valueOf(noOfSets));

//                adapter = new SetAdapter(setsIDs);
//                setsView.setAdapter(adapter);


                SetsAdapter adapter = new SetsAdapter(setsIDs.size());
                sets_grid.setAdapter(adapter);

                loadingDialog.dismiss();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(SetsActivity.this,"There is no sets in This activites", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            SetsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}