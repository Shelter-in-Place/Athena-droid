package com.example.athena_droid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArticleViewModel articleViewModel;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ArticleAdapter adapter = new ArticleAdapter();
        recyclerView.setAdapter(adapter);

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        articleViewModel.getAllArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                adapter.setArticles(articles);
            }
        });

        toolbar = findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
    }

    public void buttonArticleClick(View view){
        Button b = (Button) view;
        int articleNumber = b.getId() - 1;
        Intent i = new Intent(MainActivity.this, ReadScreenActivity.class);
        i.putExtra("Article ID", articleNumber);
        MainActivity.this.startActivity(i);
    }

}
