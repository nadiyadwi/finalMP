package com.example.firebasemp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebasemp.Diary;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private List<Diary> diaryList;

    public DiaryAdapter(List<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Diary diary = diaryList.get(position);
        holder.diaryContent.setText(diary.getText());
        holder.diaryTimestamp.setText(diary.getDate());
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public static class DiaryViewHolder extends RecyclerView.ViewHolder {
        TextView diaryContent, diaryTimestamp;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            diaryContent = itemView.findViewById(R.id.text_diary_content);
            diaryTimestamp = itemView.findViewById(R.id.text_diary_timestamp);
        }
    }
}
