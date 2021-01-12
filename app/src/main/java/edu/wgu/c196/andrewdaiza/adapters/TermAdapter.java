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
import edu.wgu.c196.andrewdaiza.database.entities.Term;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.checkDatesEqual;
import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateFormatting;

public class TermAdapter extends ListAdapter<Term, TermAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Term> DIFF_CALL_BACK = new DiffUtil.ItemCallback<Term>() {
        @Override
        public boolean areItemsTheSame(@NonNull Term oldItem, @NonNull Term newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Term oldItem, @NonNull Term newItem) {
            boolean titlesMatch = oldItem.getTerm_title().equals(newItem.getTerm_title());
            boolean startDateMatch = checkDatesEqual(oldItem.getTerm_startDate(), newItem.getTerm_startDate());
            boolean endDateMatch = checkDatesEqual(oldItem.getTerm_endDate(), newItem.getTerm_endDate());
            return titlesMatch && startDateMatch && endDateMatch;
        }
    };

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Term currentTerm = getItem(position);
        holder.titleText.setText(currentTerm.getTerm_title());
        Date startDate = currentTerm.getTerm_startDate();
        Date endDate = currentTerm.getTerm_endDate();
        String startDateStr = startDate == null ? "????" : getDateFormatting(startDate);
        String endDateStr = endDate == null ? "???? " : getDateFormatting(endDate);
        String dateRange = startDateStr + " - " + endDateStr;
        holder.dateRangeText.setText(dateRange);
    }


    public void setOnClickListener(ClickListener<Term> clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView dateRangeText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.term_title_text_view);
            dateRangeText = itemView.findViewById(R.id.term_date_text_view);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });
        }
    }

    private ClickListener<Term> clickListener;

    public TermAdapter() {
        super(DIFF_CALL_BACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_term, parent, false);
        return new ViewHolder(view);
    }


}
