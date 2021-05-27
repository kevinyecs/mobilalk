package com.example.service_catalog_mobile.api;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.service_catalog_mobile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateServiceCatalog extends AppCompatActivity {
    private static final String LOG_TAG = AddServiceCatalog.class.getName();
    EditText name, href, description, validforStart,validforEnd, lifecycleStatus, version,  lastUpdate;
    Spinner relatedParty, serviceCategoryRefs;


    Button add, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service_catalog);

        Bundle extras = this.getIntent().getExtras();
        String CURRENT_ID = extras.getString("CURRENT_ID");


        name=(EditText)findViewById(R.id.addname);
        href=(EditText)findViewById(R.id.addhref);
        description=(EditText) findViewById(R.id.adddesc);
        version=(EditText) findViewById(R.id.version);
        lastUpdate=(EditText) findViewById(R.id.add_lastUpdate);
        lifecycleStatus=(EditText)findViewById(R.id.addlifecycle);

        validforStart=(EditText)findViewById(R.id.validForStart);
        validforEnd=(EditText)findViewById(R.id.validForEnd);



        back =(Button) findViewById(R.id.back_button);
        back.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),ServiceCatalogListActivity.class));
            finish();
        });

        add = (Button) findViewById(R.id.update_fb);
        add.setOnClickListener(v -> {
            try {
                updateService(CURRENT_ID);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finish();
        });
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference categoryRef = rootRef.collection("category");
        CollectionReference relatedRef = rootRef.collection("RelatedParty");


        Spinner spinner = findViewById(R.id.spinner);
        List<String> categories = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        categoryRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String subject = document.getString("id");
                    categories.add(subject);
                }
                adapter.notifyDataSetChanged();
            }
        });

        Spinner spinner2 = findViewById(R.id.spinner2);
        List<String> rpids = new ArrayList<>();
        ArrayAdapter<String> adapterR = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, rpids);
        adapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapterR);
        relatedRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String ids = document.getString("id");
                    rpids.add(ids);
                }
                adapterR.notifyDataSetChanged();
            }
        });

        relatedParty= findViewById(R.id.spinner2);
        serviceCategoryRefs= findViewById(R.id.spinner);


    }
    private void updateService(String cID) throws ParseException {

        Map<String, Object> map = new HashMap<>();
       DocumentReference docRef = FirebaseFirestore.getInstance().collection("servicecatalogs").document(cID);


        map.put("name", name.getText().toString());
        map.put("href",href.getText().toString());
        map.put("description",description.getText().toString());
        map.put("version",version.getText().toString());
        map.put("lifecycleStatus",lifecycleStatus.getText().toString());

        List<Date> validFor = new ArrayList<>();
        Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(validforStart.getText().toString());

        Date d2 = new SimpleDateFormat("dd/MM/yyyy").parse(validforEnd.getText().toString());

        Date lu = new SimpleDateFormat("dd/MM/yyyy").parse(lastUpdate.getText().toString());

        validFor.add(d1);
        validFor.add(d2);
        map.put("validFor", validFor);
        map.put("lastUpdate", lu);



      docRef.update(map).addOnSuccessListener(aVoid -> Log.d(LOG_TAG,"Jó")).addOnFailureListener(e -> Log.d(LOG_TAG,"nem jó" ,e));





    }
}