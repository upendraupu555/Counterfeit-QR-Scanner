package com.android.cqr2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Customer extends AppCompatActivity {
    Button btScan;
    TextView tv,tv2;
    Button bt_logout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        btScan = findViewById(R.id.bt_scan);
        tv = findViewById(R.id.text);
        tv2 = findViewById(R.id.text2);
        btScan.setOnClickListener(view -> startActivity(new Intent(Customer.this,qrscanner.class)));

        bt_logout = findViewById(R.id.bt_logout);
        Intent i = getIntent();
        String dene = (String) i.getSerializableExtra("sampleObject");
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ProductInfo");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                if(map.containsValue(dene))
                    tv.setText("Genuine Product");
                else
                    tv2.setText("Fake Product");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        bt_logout.setOnClickListener(view -> logout());
    }
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Customer.this, MainActivity.class));
    }

}