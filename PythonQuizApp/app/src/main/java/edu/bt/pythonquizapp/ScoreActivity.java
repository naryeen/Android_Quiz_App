package edu.bt.pythonquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private Button done;

    private TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score = findViewById(R.id.sa_score);
        done = findViewById(R.id.sa_done);

        String score_Str = getIntent().getStringExtra("SCORE");
        score.setText(score_Str);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
                ScoreActivity.this.startActivity(intent);
                ScoreActivity.this.finish();
            }
        });

    }
}