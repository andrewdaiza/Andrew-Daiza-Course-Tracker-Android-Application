package edu.wgu.c196.andrewdaiza.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.database.entities.Assessment;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.checkDatesEqual;
import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateFormatting;

public class AssessmentAdapter extends ListAdapter<Assessment, AssessmentAdapter.ViewAssessmentHold> {

    private static final DiffUtil.ItemCallback<Assessment> DIFF_CALL_BACK = new DiffUtil.ItemCallback<Assessment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Assessment oldItem, @NonNull Assessment newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Assessment oldItem, @NonNull Assessment newItem) {
            boolean titlesMatch = oldItem.getAssessment_title().equals(newItem.getAssessment_title());
            boolean courseIdMatch = oldItem.getCourse_Id() == newItem.getCourse_Id();
            boolean completionDateMatch = checkDatesEqual(oldItem.getAssessment_completionDate(), newItem.getAssessment_completionDate());
            return titlesMatch && courseIdMatch && completionDateMatch;
        }
    };

    @Override
    public void onBindViewHolder(@NonNull ViewAssessmentHold holder, int position) {
        Assessment currentAssessment = getItem(position);
        holder.mAssessmentTitle.setText(currentAssessment.getAssessment_title());
        Date cal = currentAssessment.getAssessment_completionDate();
        String completionDate = cal == null ? "???? " : getDateFormatting(cal);
        holder.mDateComplete.setText(completionDate);
    }

    protected class ViewAssessmentHold extends RecyclerView.ViewHolder {
        private final TextView mAssessmentTitle;
        private final TextView mDateComplete;

        public ViewAssessmentHold(@NonNull View itemView) {
            super(itemView);
            mAssessmentTitle = itemView.findViewById(R.id.assessment_title_view_text);
            mDateComplete = itemView.findViewById(R.id.text_view_assessment_completion_date);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });
        }
    }

    private ClickListener<Assessment> clickListener;

    public AssessmentAdapter() {
        super(DIFF_CALL_BACK);
    }

    public void setClickListener(ClickListener<Assessment> clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewAssessmentHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assessment, parent, false);
        return new ViewAssessmentHold(view);
    }


}
