package sample.architecture_components.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import sample.architecture_components.entities.Word;

@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Query("DELETE from word_table")
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word asc")
    LiveData<List<Word>> getAllWords();
}
