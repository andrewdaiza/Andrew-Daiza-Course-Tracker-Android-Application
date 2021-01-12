package edu.wgu.c196.andrewdaiza.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.database.entities.Course;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.checkDatesEqual;


public class CourseAdapter extends ListAdapter<Course, CourseAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Course> DIFF_CALL_BACK = new DiffUtil.ItemCallback<Course>() {
        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            boolean titlesMatch = oldItem.getCourse_title().equals(newItem.getCourse_title());
            boolean termIdMatch = oldItem.getTerm_Id() == newItem.getTerm_Id();
            boolean statusMatch = oldItem.getCourse_status().equals(newItem.getCourse_status());
            boolean startDateMatch = checkDatesEqual(oldItem.getCourse_startDate(), newItem.getCourse_startDate());
            boolean endDateMatch = checkDatesEqual(oldItem.getCourse_endDate(), newItem.getCourse_endDate());
            return titlesMatch && termIdMatch && statusMatch && startDateMatch && endDateMatch;
        }
    };
    private ClickListener<Course> clickListener;

    public CourseAdapter() {
        super(DIFF_CALL_BACK);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        Course currentCourse = getItem(position);
        String title = currentCourse.getCourse_title();
        holder.titleText.setText(title);
    }

    public void setOnClickListener(ClickListener<Course> clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.item_title_view_text);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });
        }
    }
}
