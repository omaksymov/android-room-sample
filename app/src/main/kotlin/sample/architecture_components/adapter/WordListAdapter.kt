package sample.architecture_components.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import sample.architecture_components.R
import sample.architecture_components.persistence.Word

class WordListAdapter internal constructor(context: Context)
    : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var words: List<Word> = emptyList() // Cached copy of words

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = layoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = words[position]
        holder.wordItemView.text = currentWord.word
    }

    override fun getItemCount(): Int = words.size

    fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }
}
