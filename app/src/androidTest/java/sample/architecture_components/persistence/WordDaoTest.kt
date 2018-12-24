package sample.architecture_components.persistence

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import java.util.ArrayList

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
//import sample.architecture_components.AndroidRoomSample

@RunWith(AndroidJUnit4::class)
class WordDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wordDao: WordDao
    private lateinit var db: WordRoomDatabase

    @Before
    fun createDb() {
        val appContext = ApplicationProvider.getApplicationContext<Application>()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(appContext, WordRoomDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        wordDao = db.wordDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testInsert() {
        val word = Word("test")
        wordDao.insert(word)
        val words = TestUtil.getData(wordDao.getAllWords())
        assertThat(words[0].word, `is`(word.word))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testGetAll() {
        val word1 = Word("test1")
        wordDao.insert(word1)
        val word2 = Word("test2")
        wordDao.insert(word2)
        val expectedWords = object : ArrayList<Word>() {
            init {
                add(word1)
                add(word2)
            }
        }

        val words = TestUtil.getData(wordDao.getAllWords())
        assertThat(words, `is`(equalTo<List<Word>>(expectedWords)))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDeleteAll() {
        // Given
        val word1 = Word("test1")
        wordDao.insert(word1)
        val word2 = Word("test2")
        wordDao.insert(word2)
        // When
        wordDao.deleteAll()
        // Then
        val words = TestUtil.getData(wordDao.getAllWords())
        assertThat(words.isEmpty(), `is`(true))
    }

}