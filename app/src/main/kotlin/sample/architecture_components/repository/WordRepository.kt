package sample.architecture_components.repository

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import sample.architecture_components.persistence.Word
import sample.architecture_components.persistence.WordDao

class WordRepository(private val wordDao: WordDao) {
    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}
