package com.android.cqr2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText email,password;
    Button register;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(view -> Register());

        login.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this,MainActivity.class)));

    }

    private void Register() {
        String user = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if(user.isEmpty()){
            email.setError("Email cannot be empty");
        }
        if(pass.isEmpty()){
            password.setError("Password cannot be empty");
        }
        else {
            mAuth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Registration unsuccessful"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}