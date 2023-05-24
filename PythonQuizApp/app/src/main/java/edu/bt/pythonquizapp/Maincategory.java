package edu.bt.pythonquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Maincategory extends AppCompatActivity {
    public TextView title;
    public Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincategory);

        title = findViewById(R.id.main_title);
        start = findViewById(R.id.btn_main_start);

        Typeface typeface = ResourcesCompat.getFont(this,R.font.blacklist);
        title.setTypeface(typeface);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Maincategory.this,CategoryActivity.class);
                startActivity(intent);
            }
        });

    }
}