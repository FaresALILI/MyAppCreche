package com.techno.ma_creche;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.techno.ma_creche.dao.MyActivite;

public class AffichageCoursActivity extends AppCompatActivity {
    FirebaseStorage storage;
    EditText editTextDescription;
    TextView textViewDateEnvoi;
    TextView textViewStatut;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;
    FirebaseStorage reference;
    StorageReference storageReference;
    Button buttonTelecharger;
    ListView listViewFichier;
    String activite;
    MyActivite myActivite;
    private String[] myFiles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_cours);
        databaseReference = FirebaseDatabase.getInstance().getReference("activites");
        reference = FirebaseStorage.getInstance();
        this.editTextDescription =findViewById(R.id.editTextDescription);
        this.textViewDateEnvoi = findViewById(R.id.textViewDateEnvoi);
        this.textViewStatut = findViewById(R.id.textViewStatut);
        this.buttonTelecharger = findViewById(R.id.buttonTelecharger);
        this.listViewFichier = findViewById(R.id.listViewFichier);
        this.activite = "Dessin";
        myFiles = new String[100];


        String idActivity =  getIntent().getStringExtra("currentActivity");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (snap.getKey().equals(idActivity)){
                        myActivite = snap.getValue(MyActivite.class);
                        myFiles = (String[]) myActivite.getListFiles().toArray();
                        //+ " / " + myActivity.getDescription().toString();

                        System.out.println("lamineA ... ");

                        textViewDateEnvoi.setText(myActivite.getDateActivity());
                        editTextDescription.setText(myActivite.getDescription());
                        if(myActivite.isEtat())
                            textViewStatut.setText("Finalisée"+" OUI");
                        else
                            textViewStatut.setText("Finalisée"+" NON");


                        System.out.println("--->"+myActivite.getIdActivity());
                        System.out.println("--->"+myActivite.getDateActivity());
                        ////////****************
                        int taille = 0;
                        for (String s : myFiles) {
                            if (s != null)
                                taille++;
                        }
                        String[] myListFileRelle = new String[taille];
                        int j = 0;
                        for (String s : myListFileRelle) {
                            if (s != null) {
                                myListFileRelle[j] = s;
                                j++;
                            }
                        }


                        //****************
                        arrayAdapter = new ArrayAdapter<String>(AffichageCoursActivity.this, android.R.layout.simple_list_item_1, myListFileRelle);
                        listViewFichier.setAdapter(arrayAdapter);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        this.buttonTelecharger.setOnClickListener(v->{
            if(this.listViewFichier.getCount()>0){
                //Lancement du téléchargement des pièces jointes
                for(int i=0;i<this.listViewFichier.getCount();i++) {
                    download(this.activite, this.listViewFichier.getItemAtPosition(i).toString().split("/")[0],this.listViewFichier.getItemAtPosition(i).toString().split("/")[1]);
                }
            }

        });
    }





    private void download(String activite,String nomFichier,String extension) {/*
        StorageReference storageReference =storage.getInstance().getReference().child("ResMyAppCreche").child(activite);
        reference = storageReference.child(nomFichier);
        reference.getDownloadUrl().addOnSuccessListener(uri->{
            String url=uri.toString();
            downloadFile(AffichageCoursActivity.this,nomFichier,extension, Environment.DIRECTORY_DOWNLOADS,url);
        }).addOnFailureListener(e->{
        });
*/
    }
    private void downloadFile(Context context, String fileName, String extension, String destination, String url) {
        DownloadManager downloadManager = (DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request =new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destination,fileName+extension);
        downloadManager.enqueue(request);
    }



}
