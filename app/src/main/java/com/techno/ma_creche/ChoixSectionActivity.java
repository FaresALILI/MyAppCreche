package com.techno.ma_creche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ChoixSectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_section);
        Button btnPs1 =findViewById(R.id.buttonCl1);
        Button btnPs2 =findViewById(R.id.buttonCl2);
        Button btnPs3 =findViewById(R.id.buttonCl3);
        Button btnMs1 =findViewById(R.id.buttonCl4);
        Button btnMs2 =findViewById(R.id.buttonCl5);
        Button btnMs3 =findViewById(R.id.buttonCl6);
        Button btnGs1 =findViewById(R.id.buttonCl7);
        Button btnGs2 =findViewById(R.id.buttonCl8);
        Button btnGs3 =findViewById(R.id.buttonCl9);

        Intent intent = new Intent(this,BlocActivity.class);
        btnPs1.setOnClickListener(v->{
            intent.putExtra("section","ps1");
            startActivity(intent);

        });
        btnPs2.setOnClickListener(v->{
            intent.putExtra("section","ps2");
            startActivity(intent);

        });
        btnPs3.setOnClickListener(v->{
            intent.putExtra("section","ps3");
            startActivity(intent);

        });
        btnMs1.setOnClickListener(v->{
            intent.putExtra("section","ms1");
            startActivity(intent);

        });
        btnMs2.setOnClickListener(v->{
            intent.putExtra("section","ms2");
            startActivity(intent);

        });
        btnMs3.setOnClickListener(v->{
            intent.putExtra("section","ms3");
            startActivity(intent);

        });
        btnGs1.setOnClickListener(v->{
            intent.putExtra("section","gs1");
            startActivity(intent);

        });
        btnGs2.setOnClickListener(v->{
            intent.putExtra("section","ps2");
            startActivity(intent);

        });
        btnGs3.setOnClickListener(v->{
            intent.putExtra("section","gs3");
            startActivity(intent);

        });

    }
}
