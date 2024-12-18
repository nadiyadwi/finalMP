package com.example.firebasemp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.firebasemp.databinding.ActivityHomePageBinding;

public class homePageActivity extends AppCompatActivity {

    private ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ambil email dari Intent
        String email = getIntent().getStringExtra("USER_EMAIL");

        // Set teks email di TextView
        if (email != null && !email.isEmpty()) {
            binding.emailTextView.setText("Welcome, " + email);
        } else {
            binding.emailTextView.setText("Welcome, Guest"); // Penanganan jika email kosong
        }

        // Tombol untuk menulis diary
        binding.buttonWriteDiary.setOnClickListener(view -> {
            Intent intent = new Intent(homePageActivity.this, Write_Diary_Activity.class);
            intent.putExtra("USER_EMAIL", email); // Kirim email ke Write_Diary_Activity
            startActivity(intent);
        });

        // Tombol untuk melihat diary
        binding.buttonSeeDiary.setOnClickListener(view -> {
            Intent intent = new Intent(homePageActivity.this, SeeDiaryActivity.class);
            intent.putExtra("USER_EMAIL", email);
            startActivity(intent);
        });

        binding.btncamera.setOnClickListener(view -> {
            Intent intent = new Intent(homePageActivity.this, cameraActivity.class);
            intent.putExtra("USER_EMAIL", email);
            startActivity(intent);
        });

    }
}
