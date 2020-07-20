package com.example.vegetablesdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vegetablesdelivery.Common.Common;
import com.example.vegetablesdelivery.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    EditText phone, password;
    Button btnSignIn;
    FirebaseDatabase database;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setupVariables();
    }

    void setupVariables() {
        phone = findViewById(R.id.edtPhone);
        password = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(phone.getText().toString()).exists()) {
                            User user = snapshot.child(phone.getText().toString()).getValue(User.class);
                            user.setPhone(phone.getText().toString());
                            if (user.getPassword().equals(password.getText().toString())) {
                                Toast.makeText(SignInActivity.this, "SignIn Successfully !", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                Common.currentUser = user;
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignInActivity.this, "SignIn Failed !", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SignInActivity.this, "User not Exist in Database", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }
    }
}