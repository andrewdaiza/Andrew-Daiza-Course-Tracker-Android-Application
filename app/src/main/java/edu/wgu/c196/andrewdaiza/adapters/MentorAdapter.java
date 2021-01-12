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
import edu.wgu.c196.andrewdaiza.database.entities.Mentor;

public class MentorAdapter extends ListAdapter<Mentor, MentorAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Mentor> DIFF_CALL_BACK = new DiffUtil.ItemCallback<Mentor>() {
        @Override
        public boolean areItemsTheSame(@NonNull Mentor oldItem, @NonNull Mentor newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Mentor oldItem, @NonNull Mentor newItem) {
            boolean courseMatch = oldItem.getCourse_Id() == newItem.getCourse_Id();
            boolean firstNameMatch = TextUtils.equals(oldItem.getMentor_firstName(), newItem.getMentor_firstName());
            boolean lastNameMatch = TextUtils.equals(oldItem.getMentor_lastName(), newItem.getMentor_lastName());
            boolean phoneMatch = TextUtils.equals(oldItem.getMentor_phoneNumber(), newItem.getMentor_phoneNumber());
            boolean emailMatch = TextUtils.equals(oldItem.getMentor_email(), newItem.getMentor_email());
            return courseMatch && firstNameMatch && lastNameMatch && phoneMatch && emailMatch;
        }
    };

    @Override
    public void onBindViewHolder(@NonNull MentorAdapter.ViewHolder holder, int position) {
        Mentor currentMentor = getItem(position);
        holder.nameText.setText(String.format("%s %s", currentMentor.getMentor_firstName(), currentMentor.getMentor_lastName()));
        holder.phoneText.setText(currentMentor.getMentor_phoneNumber());
        holder.emailText.setText(currentMentor.getMentor_email());
    }

    public void setOnClickListener(ClickListener<Mentor> clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final TextView phoneText;
        private final TextView emailText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.mentor_name_text_view);
            phoneText = itemView.findViewById(R.id.mentor_phone_text_view);
            emailText = itemView.findViewById(R.id.mentor_email_text_view);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });
        }
    }

    private ClickListener<Mentor> clickListener;

    public MentorAdapter() {
        super(DIFF_CALL_BACK);
    }

    @NonNull
    @Override
    public MentorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mentor, parent, false);
        return new MentorAdapter.ViewHolder(view);
    }


}
