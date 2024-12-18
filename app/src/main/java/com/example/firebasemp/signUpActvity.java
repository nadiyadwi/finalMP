package com.example.firebasemp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.firebasemp.databinding.ActivityMainBinding;
import com.example.firebasemp.databinding.ActivitySignUpActvityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signUpActvity extends AppCompatActivity {

    private ActivitySignUpActvityBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpActvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        setupAction();
    }

    private void setupAction() {
        binding.tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signUpActvity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(signUpActvity.this, "complete data", Toast.LENGTH_SHORT).show();
                }else{
                    buatAkun(email, password);
                }
            }
        });
    }

    private void buatAkun(String email, String password) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(signUpActvity.this, "successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(signUpActvity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signUpActvity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}