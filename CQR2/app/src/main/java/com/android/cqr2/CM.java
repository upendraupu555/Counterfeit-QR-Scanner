package com.android.cqr2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CM extends AppCompatActivity {
    Button customer;
    Button manufacturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_m);

        customer = findViewById(R.id.bt_customer);
        manufacturer = findViewById(R.id.bt_manufacturer);

        customer.setOnClickListener(view -> startActivity(new Intent(CM.this,Customer.class)));
        manufacturer.setOnClickListener(view -> startActivity(new Intent(CM.this,Manufacturer.class)));
    }
}