package com.example.mydom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class user_view extends AppCompatActivity implements View.OnClickListener {

    TextView TVname, TVsecondname, TVsurname;
    FirebaseFirestore firestore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        TVname = (TextView) findViewById(R.id.name);
        TVsecondname = (TextView) findViewById(R.id.secondname);
        TVsurname = (TextView) findViewById(R.id.surname);

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference ref = firestore.collection(user.getEmail()).document("userData");
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                TVname.setText(snapshot.get("name").toString());
                TVsecondname.setText(snapshot.get("secondname").toString());
                TVsurname.setText(snapshot.get("surname").toString());
            }
        });
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_addApartment).setOnClickListener(this);
        findViewById(R.id.btn_apartment).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_edit) {
            Intent intent = new Intent(user_view.this, userEdit.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_addApartment){
            Intent intent = new Intent(user_view.this, AddApartment.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_apartment){
            Intent intent = new Intent(user_view.this, hotels.class);
            startActivity(intent);
        }
    }
}

