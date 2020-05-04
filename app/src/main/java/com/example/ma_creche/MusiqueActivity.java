package com.example.myappcom.enfants;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ma_creche.R;
//import com.example.myappcom.UTILS.MediaUtils;

import java.util.ArrayList;

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
