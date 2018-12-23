package sample.architecture_components.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static volatile WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addCallback(sCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WordDao wordDao();

    private static RoomDatabase.Callback sCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        WordDao wordDao;

        public PopulateDBAsyncTask(WordRoomDatabase dbInstance) {
            wordDao = dbInstance.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAll();
            Word word = new Word("Room1");
            wordDao.insert(word);
            word = new Word("Room2");
            wordDao.insert(word);
            return null;
        }
    }
}
