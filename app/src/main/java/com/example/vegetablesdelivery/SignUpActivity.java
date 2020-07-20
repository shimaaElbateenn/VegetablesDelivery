package com.example.vegetablesdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vegetablesdelivery.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText phone, name, password;
    Button btnSignUn;
    FirebaseDatabase database;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupVariables();
    }

    void setupVariables() {
        phone = findViewById(R.id.edtPhone);
        password = findViewById(R.id.edtPassword);
        name = findViewById(R.id.edtName);
        btnSignUn = findViewById(R.id.btnSignUp);
        btnSignUn.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(phone.getText().toString()).exists()){
                            Toast.makeText(SignUpActivity.this, "Phone Number Already Exist", Toast.LENGTH_LONG).show();
                        } else {
                            User user = new User(name.getText().toString(), password.getText().toString(), phone.getText().toString());
                            table_user.child(phone.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }
    }
}