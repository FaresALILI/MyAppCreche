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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.techno.ma_creche.dao.FichierDistant;
import com.techno.ma_creche.dao.MyActivite;

import java.util.ArrayList;

public class AffichageCoursActivity extends AppCompatActivity {
    FirebaseStorage storage;
    EditText editTextDescription;
    EditText editTextObjet;
    TextView textViewDateEnvoi;
    TextView textViewStatut;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;
    FirebaseStorage reference;
    Button buttonTelecharger;
    ListView listViewFichier;
    String activite;
    MyActivite myActivite;
    String[] myFiles;
    ArrayList<FichierDistant> listfiles=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_cours);
        databaseReference = FirebaseDatabase.getInstance().getReference("activites");
        reference = FirebaseStorage.getInstance();
        this.editTextDescription = findViewById(R.id.editTextDescription);
        this.editTextObjet = findViewById(R.id.editTextObjet);
        this.textViewDateEnvoi = findViewById(R.id.textViewDateEnvoi);
        this.textViewStatut = findViewById(R.id.textViewStatut);
        this.buttonTelecharger = findViewById(R.id.buttonTelecharger);
        this.listViewFichier = findViewById(R.id.listViewFichier);
        this.activite = "Dessin";
        myFiles = new String[100];
        myActivite = new MyActivite();
        myActivite = (MyActivite) getIntent().getSerializableExtra("currentActivity");
        textViewDateEnvoi.setText(myActivite.getDateActivity());
        editTextDescription.setText(myActivite.getDescription());
        editTextObjet.setText(myActivite.getObjet());

        if (myActivite.isEtat())
            textViewStatut.setText("Finalisée" + " OUI");
        else
            textViewStatut.setText("Finalisée" + " NON");

        Object[] tabObj = myActivite.getListFiles().toArray();

        for (int i = 0; i < tabObj.length; i++) {
            String nomFile = tabObj[i].toString().split("nomFile=")[1].split(",")[0];//.split("}")[0];
                 myFiles[i] = nomFile;
            FichierDistant fd = new FichierDistant();
            fd.setLink(nomFile);
            listfiles.add(fd);
            int taille = 0;
            for (String s : myFiles) {
                if (s != null)
                    taille++;
            }
            String[] myFilessRelle = new String[taille];
            int j = 0;
            for (String s : myFiles) {
                if (s != null) {
                    myFilessRelle[j] = s;
                    j++;
                }
            }

            arrayAdapter = new ArrayAdapter<String>(AffichageCoursActivity.this, android.R.layout.simple_list_item_1, myFilessRelle);
            listViewFichier.setAdapter(arrayAdapter);

            this.buttonTelecharger.setOnClickListener(v -> {
                if (this.listfiles.size() > 0) {
                    //Lancement du téléchargement des pièces jointes
                    for (int k = 0; k < this.listfiles.size(); k++) {
                        download(this.activite, this.listfiles.get(k).getLink(), this.listfiles.get(k).getExtFile());
                    }
                }
            });
        }
    }

    private void download(String activite,String nomFichier,String extension) {
        StorageReference storageReference =storage.getInstance().getReference().child("ResMyAppCreche").child(activite);
        storageReference = storageReference.child(nomFichier);
        storageReference.getDownloadUrl().addOnSuccessListener(uri->{
            String url=uri.toString();
            downloadFile(AffichageCoursActivity.this,nomFichier,extension, Environment.DIRECTORY_DOWNLOADS,url);
        }).addOnFailureListener(e->{
        });

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