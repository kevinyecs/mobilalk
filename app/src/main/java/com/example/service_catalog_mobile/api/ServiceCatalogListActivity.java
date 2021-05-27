package com.example.service_catalog_mobile.api;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_catalog_mobile.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import java.util.ArrayList;

public class ServiceCatalogListActivity extends AppCompatActivity {
    private static final String LOG_TAG = ServiceCatalogListActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<ServiceCatalog> mServiceCatalogList;
    private ServiceCatalogAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private NotificationHelper mNotificationHelper;


    private FirebaseFirestore mFirestore;
    private CollectionReference mCollection;

    private int gridNumber = 1;
    private int queryLimit = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_catalog_list);
        mAuth = FirebaseAuth.getInstance();
        // mAuth.signOut();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }




        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mServiceCatalogList = new ArrayList<>();

        mAdapter = new ServiceCatalogAdapter(this, mServiceCatalogList);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mCollection = mFirestore.collection("servicecatalogs");

        queryData();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        this.registerReceiver(null, filter);
        mNotificationHelper = new NotificationHelper(this);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fadd);
        mFloatingActionButton.setOnClickListener(v -> {
        startActivity(new Intent(getApplicationContext(), AddServiceCatalog.class));
        });

}


    private void queryData(){
        mServiceCatalogList.clear();

        mCollection.orderBy("name", Query.Direction.DESCENDING).limit(queryLimit).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                ServiceCatalog item = doc.toObject(ServiceCatalog.class);
                mServiceCatalogList.add(item);
            }
            if(mServiceCatalogList.size() == 0){

                queryData();
            }
            mAdapter.notifyDataSetChanged();
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.catalog_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){


            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.log_out_button:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.settings_button:
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void deleteItem(ServiceCatalog item) {
        DocumentReference ref = mCollection.document(item.getId());
        ref.delete()
                .addOnSuccessListener(success -> {
                    Log.d(LOG_TAG, "Item is successfully deleted: " + item.getId());
                })
                .addOnFailureListener(fail -> {
                    Toast.makeText(this, "Item " + item.getId() + " cannot be deleted.", Toast.LENGTH_LONG).show();
                });

        queryData();
        mNotificationHelper.cancel();
    }

    public void updateItem(ServiceCatalog item){
        DocumentReference ref = mCollection.document(item.getId());


    }

}
