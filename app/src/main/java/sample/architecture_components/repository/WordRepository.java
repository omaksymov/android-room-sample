package sample.architecture_components.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import sample.architecture_components.persistence.WordDao;
import sample.architecture_components.persistence.WordRoomDatabase;
import sample.architecture_components.persistence.Word;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getInstance(application);
        wordDao = db.wordDao();
        allWords = wordDao.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert(Word word) {
        new InsertWordAsyncTask(wordDao).execute(word);
    }

    private static class InsertWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao taskWordDao;

        public InsertWordAsyncTask(WordDao wordDao) {
            taskWordDao = wordDao;
        }

        @Override
        protected Void doInBackground(final Word... words) {
            taskWordDao.insert(words[0]);
            return null;
        }
    }

}
