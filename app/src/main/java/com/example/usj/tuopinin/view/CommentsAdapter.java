package com.example.usj.tuopinin.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.usj.tuopinin.R;
import com.example.usj.tuopinin.model.entities.Comment;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private List<Comment> commentsList;

    public CommentsAdapter(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_view_item, viewGroup, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.commentTextView.setText(commentsList.get(i).getComment());
        commentViewHolder.ratingTextView.setText(String.valueOf(commentsList.get(i).getRating()));
        commentViewHolder.ratingBar.setRating(commentsList.get(i).getRating());
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        public TextView commentTextView;
        public TextView ratingTextView;
        public RatingBar ratingBar;

        public CommentViewHolder(View v) {
            super(v);
            commentTextView = v.findViewById(R.id.commentsTextView);
            ratingTextView = v.findViewById(R.id.rateNumberTextView);
            ratingBar = v.findViewById(R.id.ratingBar);
        }
    }
}
