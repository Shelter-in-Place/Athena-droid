package com.example.athena_droid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.List;

public class ReadScreenViewModel extends AndroidViewModel {

    // Declarations for repository (for article data)
    private ArticleRepository repository;
    private LiveData<List<Article>> allArticles;

    // Declarations for Firebase ML translator API
    private FirebaseTranslatorOptions options;
    private FirebaseTranslator italianEnglishTranslator;
    private FirebaseModelDownloadConditions conditions;
    private boolean translateModelDownloadStatus;

    private MutableLiveData<String> translatedText;
    private ReadScreenViewModel readScreenViewModel;

    public ReadScreenViewModel(@NonNull Application application) {
        super(application);
        readScreenViewModel = this;

        // Instantiate repository for article data
        this.repository = new ArticleRepository(application);
        this.allArticles = repository.getAllArticles();
        this.translatedText = new MutableLiveData<String>();
        this.translatedText.setValue("Translate Text");

        // Instantiate Firebase ML translator objects:
        this.options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(FirebaseTranslateLanguage.IT)
                .setTargetLanguage(FirebaseTranslateLanguage.EN)
                .build();
        this.italianEnglishTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);
        // Download Firebase translation model if needed
        this.conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        translateModelDownload();
    }

    public void translateModelDownload(){
        this.italianEnglishTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
                                translateModelDownloadStatus = true;

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                translateModelDownloadStatus = false;
                            }
                        });
    }

    public void translateText(CharSequence inputChar){
        final String inputText= inputChar.toString();
        this.italianEnglishTranslator.translate(inputText)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                readScreenViewModel.translatedText.setValue("[Translation successful]: " + translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                readScreenViewModel.translatedText.setValue("[Translation failed]: " + inputText);
                            }
                        });

    }

    public LiveData<String> getTranslatedText(){
        return translatedText;
    }

    public void insert(Article article){
        repository.insert(article);
    }

    public void update(Article article){
        repository.update(article);
    }

    public void delete(Article article){
        repository.delete(article);
    }

    public void deleteAllNotes(){
        repository.deleteAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

}
