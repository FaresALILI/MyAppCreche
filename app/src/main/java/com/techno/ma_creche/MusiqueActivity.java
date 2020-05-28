package com.techno.myappcom.enfants;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.techno.ma_creche.R;
//import com.example.myappcom.UTILS.MediaUtils;


public class MusiqueActivity extends AppCompatActivity {
    ArrayAdapter<String> model;
    String currentMusique;
  //  MediaUtils mediaUtils;
    TextView textViewDuree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musique);
        this.textViewDuree = findViewById(R.id.textViewDuree);
    }

}
