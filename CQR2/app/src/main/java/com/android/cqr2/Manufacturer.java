package com.android.cqr2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Manufacturer extends AppCompatActivity {
    ImageView qrCodeIV;
    EditText dataEdt;
    Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Button bt_logout;
    Button add_db;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Block block;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer);

        add_db = findViewById(R.id.add_db);
        qrCodeIV = findViewById(R.id.idIVQrcode);
        dataEdt = findViewById(R.id.idEdt);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ProductInfo");

        generateQrBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(dataEdt.getText().toString())) {

                Toast.makeText(Manufacturer.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
            } else {

                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                Display display = manager.getDefaultDisplay();

                Point point = new Point();
                display.getSize(point);

                int width = point.x;
                int height = point.y;

                int dimen = Math.min(width, height);
                dimen = dimen * 3 / 4;

                qrgEncoder = new QRGEncoder(dataEdt.getText().toString(), null, QRGContents.Type.TEXT, dimen);
                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    qrCodeIV.setImageBitmap(bitmap);
                } catch (WriterException e) {

                    Log.e("Tag", e.toString());
                }
            }
        });
        bt_logout = findViewById(R.id.bt_logout);

        add_db.setOnClickListener(view -> database());

        bt_logout.setOnClickListener(view -> logout());

    }

    public void database() {
        block = new Block(index,System.currentTimeMillis(),null,dataEdt.getText().toString());
        block.mineBlock(0);
        databaseReference.push().setValue(block.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    databaseReference.push().setValue(dataEdt.getText().toString());
                    Toast.makeText(Manufacturer.this, "data added", Toast.LENGTH_SHORT).show();}
                else
                    Toast.makeText(Manufacturer.this, "Fail to add data ", Toast.LENGTH_SHORT).show();

            }
        });
        index++;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Manufacturer.this, MainActivity.class));
    }
}