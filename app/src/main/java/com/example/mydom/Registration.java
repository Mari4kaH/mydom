package com.example.mydom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    private EditText ETemail, ETpassword, ETname, ETsecondname, ETsurename;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        auth = FirebaseAuth.getInstance();
        ETemail = (EditText) findViewById(R.id.et_email);
        ETpassword = (EditText) findViewById(R.id.et_password);
        ETname = (EditText) findViewById(R.id.et_name);
        ETsecondname = (EditText) findViewById(R.id.et_secondname);
        ETsurename = (EditText) findViewById(R.id.et_surname);
        firestore = FirebaseFirestore.getInstance();

        findViewById(R.id.submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.submit) {
            //Toast.makeText(Registration.this, "Registration is failed", Toast.LENGTH_SHORT).show();
            registration(ETemail.getText().toString(), ETpassword.getText().toString());
        }
    }
    public void registration(final String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Map<String,String> dataToFirestore = new HashMap<>();
                    dataToFirestore.put("name", ETname.getText().toString().trim());
                    dataToFirestore.put("secondname", ETsecondname.getText().toString().trim());
                    dataToFirestore.put("surname",ETsurename.getText().toString().trim());
                    firestore.collection(email).document("userData").set(dataToFirestore).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(Registration.this, user_view.class);
                            startActivity(intent);
                            Toast.makeText(Registration.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    Toast.makeText(Registration.this, "Registration is failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

