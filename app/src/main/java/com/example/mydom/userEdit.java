package com.example.mydom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class userEdit extends AppCompatActivity implements View.OnClickListener{

    EditText et_name, et_surname, et_secondname;
    FirebaseFirestore firestore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        findViewById(R.id.btn_changePassword);
        et_name = (EditText) findViewById(R.id.et_name);
        et_surname = (EditText) findViewById(R.id.et_surname);
        et_secondname = (EditText) findViewById(R.id.et_secondname);

        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.btn_changePassword).setOnClickListener(this);

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference ref = firestore.collection(user.getEmail()).document("userData");
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                et_name.setText(snapshot.get("name").toString());
                et_secondname.setText(snapshot.get("secondname").toString());
                et_surname.setText(snapshot.get("surname").toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_changePassword) {
            Intent intent = new Intent(userEdit.this, userEdit.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_submit){
            firestore.collection(user.getEmail()).document("userData").update(
                    "name", et_name.getText().toString().trim(),
                    "secondname", et_secondname.getText().toString().trim(),
                    "surname", et_surname.getText().toString().trim()
            );
            Intent intent = new Intent (userEdit.this, user_view.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_cancel){
            Intent intent = new Intent (userEdit.this, user_view.class);
            startActivity(intent);
        }
    }
}