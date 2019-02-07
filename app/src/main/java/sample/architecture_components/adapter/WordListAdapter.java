package sample.architecture_components.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sample.architecture_components.R;
import sample.architecture_components.persistence.Word;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Word> words;

    public WordListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View recyclerViewItem = layoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false);

        return new WordViewHolder(recyclerViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder wordViewHolder, int i) {
        TextView textView = wordViewHolder.wordViewHolder;
        if (words != null) {
            textView.setText(words.get(i).getWord());
        } else {
            textView.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        if (words != null) {
            return words.size();
        }
        return 0;
    }

    public void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView wordViewHolder;

        public WordViewHolder(View itemView) {
            super(itemView);
            wordViewHolder = itemView.findViewById(R.id.textView);
        }
    }
}
