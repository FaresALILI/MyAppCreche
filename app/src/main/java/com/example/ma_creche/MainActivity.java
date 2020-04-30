package com.example.ma_creche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button butonConnect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*this.butonConnect= findViewById(R.id.butonConnect);

        this.butonConnect.setOnClickListener((View v)->
                {
               Intent inte = new Intent(this,AuthentificationActivity.class);
              // inte.putExtra("nom","adam");
                    startActivity(inte);
                System.out.println("salut ....");
                }

                );*/

    }
}
