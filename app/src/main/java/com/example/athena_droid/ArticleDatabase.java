package com.example.athena_droid;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    private static ArticleDatabase instance;

    public abstract ArticleDao articleDao();

    public static synchronized ArticleDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                   ArticleDatabase.class, "article_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{


        private String article1 = "Mi chiamo Luisa e ho 16 anni. Oggi vorrei parlarvi della mia camera. La mia camera è davvero molto bella e spaziosa. A destra ci sono il mio letto e un armadio, che è sempre molto pieno dei miei vestiti! Vicino al letto si trova anche un comò dove tengo i miei libri, che spesso leggo ogni sera prima di dormire.\n" +
                "\n" +
                "Davanti al mio letto si trova la mia televisione e una poltrona su un tappeto. Spesso mi siedo e guardo la televisione per ore. La mia stanza ha anche una scrivania dove si trova un computer, che uso quando devo studiare.\n" +
                "\n" +
                "Quando faccio i miei compiti scrivo sul tavolo che si trova al centro della mia camera. Amo la mia camera perché qui posso studiare e divertirmi molto ogni giorno!; ";

        private ArticleDao articleDao;

        private String title1 = "La camera da letto di Luisa";

        private String defaultLocation = "[NO LOCATION]";

        private PopulateDbAsyncTask(ArticleDatabase db){
            articleDao = db.articleDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articleDao.insert(new Article(title1, "Description 1", 10, article1, defaultLocation));
            articleDao.insert(new Article("Title 2", "Description 2", 9, "Test String 2", defaultLocation));
            articleDao.insert(new Article("Title 3", "Description 3", 8, "Test String 3", defaultLocation));
            articleDao.insert(new Article("Title 4", "Description 4", 7, "Test String 4", defaultLocation));
            articleDao.insert(new Article("Title 5", "Description 3", 6, "Test String 5", defaultLocation));
            articleDao.insert(new Article("Title 6", "Description 3", 5, "Test String 6", defaultLocation));
            articleDao.insert(new Article("Title 7", "Description 3", 4, "Test String 7", defaultLocation));
            articleDao.insert(new Article("Title 8", "Description 3", 3, "Test String 8", defaultLocation));
            articleDao.insert(new Article("Title 9", "Description 3", 2, "Test String 9", defaultLocation));
            articleDao.insert(new Article("Title 10", "Description 3", 1, "Test String 10", defaultLocation));

            return null;
        }
    }

}
