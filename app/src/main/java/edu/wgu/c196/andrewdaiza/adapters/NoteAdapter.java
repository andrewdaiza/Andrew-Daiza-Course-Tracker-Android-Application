package edu.wgu.c196.andrewdaiza.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.database.entities.Note;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Note> DIFF_CALL_BACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            boolean courseMatch = oldItem.getCourse_Id() == newItem.getCourse_Id();
            boolean titlesMatch = oldItem.getNote_title().equals(newItem.getNote_title());
            boolean sameDesc = TextUtils.equals(oldItem.getNote_description(), newItem.getNote_description());
            return courseMatch && titlesMatch && sameDesc;
        }
    };

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Note note = getItem(position);
        holder.titleText.setText(note.getNote_title());
        holder.descriptionText.setText(note.getNote_description());
    }


    public void setOnClickListener(ClickListener<Note> clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView descriptionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.note_tile_text_view);
            descriptionText = itemView.findViewById(R.id.description_note_text_view);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });
        }
    }

    private ClickListener<Note> clickListener;

    public NoteAdapter() {
        super(DIFF_CALL_BACK);
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteAdapter.ViewHolder(view);
    }


}
