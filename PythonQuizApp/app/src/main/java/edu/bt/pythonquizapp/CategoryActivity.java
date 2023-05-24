package edu.bt.pythonquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import static edu.bt.pythonquizapp.SplashActivity.catList;


public class CategoryActivity extends AppCompatActivity {
    GridView catGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Lessons");
//
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        catGrid = findViewById(R.id.catGridview);

        //this is static list or simply local list of lessons
//        List<String >catList = new ArrayList<>();
//        catList.add("Lesson 1");
//        catList.add("Lesson 2");
//        catList.add("Lesson 3");
//        catList.add("Lesson 4");
//        catList.add("Lesson 5");
//        catList.add("Lesson 6");
        //TODO errors may occurs here
        CatGridAdapter adapter = new CatGridAdapter(catList);
        catGrid.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            CategoryActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}