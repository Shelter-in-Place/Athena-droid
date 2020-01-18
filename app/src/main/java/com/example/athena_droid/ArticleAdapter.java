package com.example.athena_droid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    private List<Article> articles = new ArrayList<>();

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);

        return new ArticleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        Article currentArticle = articles.get(position);
        holder.itemButton.setText(currentArticle.getTitle());
        holder.itemButton.setId(currentArticle.getId());
//        holder.textViewTitle.setText((currentArticle.getTitle()));
//        holder.textViewDescription.setText((currentArticle.getDescription()));
//        holder.textViewPriority.setText(String.valueOf(currentArticle.getPriority()));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Article> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }

    class ArticleHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        private Button itemButton;


        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            itemButton = itemView.findViewById(R.id.item_button);
//            textViewTitle = itemView.findViewById(R.id.text_view_title);
//            textViewDescription = itemView.findViewById(R.id.text_view_description);
//            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }

}
