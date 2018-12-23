package sample.architecture_components.persistence;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WordDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private WordDao wordDao;
    private WordRoomDatabase db;

    @Before
    public void createDb() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(appContext, WordRoomDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        wordDao = db.wordDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void testInsert() throws InterruptedException {
        Word word = new Word("test");
        wordDao.insert(word);
        List<Word> words = TestUtil.getData(wordDao.getAllWords());
        assertThat(words.get(0).getWord(), is(word.getWord()));
    }

    @Test
    public void testGetAll() throws InterruptedException {
        final Word word1 = new Word("test1");
        wordDao.insert(word1);
        final Word word2 = new Word("test2");
        wordDao.insert(word2);
        List<Word> expectedWords = new ArrayList<Word>() {{
            add(word1);
            add(word2);
        }};

        List<Word> words = TestUtil.getData(wordDao.getAllWords());
        assertThat(words, is(equalTo(expectedWords)));
    }

    @Test
    public void testDeleteAll() throws InterruptedException {
        // Given
        final Word word1 = new Word("test1");
        wordDao.insert(word1);
        final Word word2 = new Word("test2");
        wordDao.insert(word2);
        // When
        wordDao.deleteAll();
        // Then
        List<Word> words = TestUtil.getData(wordDao.getAllWords());
        assertThat(words.isEmpty(), is(true));
    }

}
