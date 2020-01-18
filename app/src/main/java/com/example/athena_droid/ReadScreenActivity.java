package com.example.athena_droid;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

public class ReadScreenActivity extends AppCompatActivity {

    private ReadScreenViewModel readScreenViewModel;
    private Article currentArticle;

    private TextView articleTitle;
    private TextView readPanelText;
    private TextView translationPannelText;
    private CharSequence selectedText;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_screen);
        toolbar = findViewById(R.id.toolBar);
        articleTitle = findViewById(R.id.article_title);
        readPanelText = findViewById(R.id.read_panel_text);
        translationPannelText = findViewById(R.id.translation_panel_text);

        Intent intent = getIntent();
        final int articleNumber = intent.getIntExtra("Article ID", 0);

        setSupportActionBar(toolbar);



        // Instantiate ReadScreenViewModel
        readScreenViewModel = ViewModelProviders.of(this).get(ReadScreenViewModel.class);
        readScreenViewModel.getAllArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                currentArticle = articles.get(articleNumber);
                articleTitle.setText(currentArticle.getTitle());
                readPanelText.setText(currentArticle.getContent());
            }
        });
        readScreenViewModel.getTranslatedText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String translatedText) {
                translationPannelText.setText(translatedText);
            }
        });


//        readPanelText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.performLongClick();
//            }
//        });

        // Dealing with select text/translate button
        readPanelText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                menu.clear();
                menu.add(0, 0, 0, "Translate");
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                translatePanel();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

        });


    }

    public void translatePanel(){
        selectedText = readPanelText.getText().subSequence(readPanelText.getSelectionStart(), readPanelText.getSelectionEnd());
        readScreenViewModel.translateText(selectedText);
    }
}
