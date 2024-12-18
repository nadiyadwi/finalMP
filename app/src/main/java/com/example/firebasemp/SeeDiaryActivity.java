package com.example.firebasemp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SeeDiaryActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private DiaryAdapter adapter;
    private List<Diary> diaryList;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_diary);

        // Ambil email pengguna dari Intent
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        if (userEmail == null || userEmail.isEmpty()) {
            Toast.makeText(this, "User email not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view_diaries);
        diaryList = new ArrayList<>();
        adapter = new DiaryAdapter(diaryList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadDiaries();
    }

    private void loadDiaries() {
        Log.d("SeeDiaryActivity", "Querying diaries for email: " + userEmail);

        db.collection("diaries")
                .whereEqualTo("email", userEmail)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        diaryList.clear();
                        if (task.getResult().isEmpty()) {
                            Log.d("SeeDiaryActivity", "No diaries found for email: " + userEmail);
                            Toast.makeText(this, "No diaries found.", Toast.LENGTH_SHORT).show();
                        }
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("SeeDiaryActivity", "Document data: " + document.getData());

                            // Ambil data text
                            String text = document.getString("text");

                            // Ambil timestamp (gunakan Timestamp Firestore)
                            Timestamp timestamp = document.getTimestamp("timestamp");

                            if (timestamp == null) {
                                Log.e("SeeDiaryActivity", "Invalid or missing timestamp in document: " + document.getId());
                                continue;
                            }

                            // Format timestamp ke String
                            String date = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
                                    .format(timestamp.toDate());

                            // Tambahkan ke list
                            diaryList.add(new Diary(text, date));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e("SeeDiaryActivity", "Query failed: ", task.getException());
                        Toast.makeText(SeeDiaryActivity.this, "Failed to load diaries.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
