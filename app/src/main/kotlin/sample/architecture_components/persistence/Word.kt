package sample.architecture_components.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Word(val word: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    // TODO: maybe need remove equals and hashCode for testing purposes
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Word

        if (word != other.word) return false

        return true
    }

    override fun hashCode(): Int {
        return word.hashCode()
    }
}