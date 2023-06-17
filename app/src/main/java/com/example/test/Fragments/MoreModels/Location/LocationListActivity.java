package com.example.test.Fragments.MoreModels.Location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LocationListActivity extends AppCompatActivity {
    TextView IntentLocation;
    ImageView Back;
    private List<LocationClass> location = new ArrayList<LocationClass>();
    private RecyclerView rv;
    View view;
    LinearLayoutManager llm;
    Context context;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String name, type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        IntentLocation = findViewById(R.id.GoToAddLocation);
        Back = findViewById(R.id.BackToMainActiv);
        rv = findViewById(R.id.locationRecycleView);
        context = LocationListActivity.this;
        llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        Back.setOnClickListener(v -> {finish();});
        IntentLocation.setOnClickListener(v -> {
            startActivity(new Intent(this, AddLocatedActivity.class));
        });
    }

    private void initializeData(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        location.clear();
        //Взять данные о его названиях карт
        db.collection("userLocated")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name = document.getString("nameLocation");
                                type = document.getString("typeLocation");
                                location.add(new LocationClass(name,type));
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                        LocationAdapter adapter = new LocationAdapter(context, location);
                        adapter.notifyDataSetChanged();
                        rv.setAdapter(adapter);
                    }
                });
    }

    private void initializeAdapter(){
        LocationAdapter adapter = new LocationAdapter(context, location);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
        initializeAdapter();
    }
    //TODO Реализовать удаление локаций
    //TODO Реализовать отображение Локаций + Разметку сделать)
}