package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab1.Adapter.ThanhPhoAdapter;
import com.example.lab1.Model.ThanhPho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    FirebaseFirestore db;
    ArrayList<ThanhPho> ccities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btnThem = findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Add.class));
            }
        });
        db = FirebaseFirestore.getInstance();
        ghiDulieu();
        docDulieu();

    }

    private void ghiDulieu() {
        CollectionReference cities = db.collection("cities");

        Map<String, Object> data1 = new HashMap<>();
        data1.put("tenThanhPho", "Hà Nội");

        cities.document("HN").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("tenThanhPho", "Tuyên Quang");
        cities.document("TQ").set(data2);

        Map<String, Object> data3 = new HashMap<>();
        data3.put("tenThanhPho", "Hà Giang");
        cities.document("HG").set(data3);

    }

    String TAG = "HomeActivity";

    private void docDulieu() {
        db.collection("cities")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                ThanhPho thanhPho = document.toObject(ThanhPho.class);
                                ccities.add(thanhPho);
                            }
                            RecyclerView view = findViewById(R.id.rcvList);
                            view.setLayoutManager(new LinearLayoutManager(Home.this));
                            ThanhPhoAdapter adapter = new ThanhPhoAdapter(Home.this, ccities);
                            view.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}