package edu.wgu.c196.andrewdaiza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.adapters.CourseAdapter;
import edu.wgu.c196.andrewdaiza.database.entities.Term;
import edu.wgu.c196.andrewdaiza.utilities.DatePickerFragment;
import edu.wgu.c196.andrewdaiza.viewmodels.EditTermViewModel;

import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateFormatting;
import static edu.wgu.c196.andrewdaiza.utilities.Converters.getDateWithFormat;

public class EditTermActivity extends AppCompatActivity {

    public static final String TERM_ID =
            "edu.wgu.c196.andrewdaiza.utilities.term_editor.TERM_ID";
    public static final String COURSE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.course_editor.COURSE_ID";

    private EditTermViewModel mViewModel;
    private CourseAdapter mAdapter;
    private Date startDate;
    private Date endDate;

    protected boolean mActivity;
    protected int mId;
    protected int mIdEntity;
    protected ViewModelProvider.Factory factory;

    private final State state = new State();

    @BindView(R.id.term_title_text)
    EditText mVarTextTitle;
    @BindView(R.id.term_start_date_value)
    TextView mStartDate;
    @BindView(R.id.term_end_date_value)
    TextView mEndDate;
    @BindView(R.id.term_course_list_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_course)
    FloatingActionButton mFab;
    @BindView(R.id.delete_term)
    Button deleteTermButton;


    private void loadState() {
        mVarTextTitle.setText(state.title);
        mStartDate.setText(state.startDate);
        mEndDate.setText(state.endDate);
    }

    protected void openActivity(Intent intent) {
        startActivity(intent);
    }

    protected void closeActivity() {
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewContent());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CourseAdapter();

        mAdapter.setOnClickListener(course -> {
            Intent intent = new Intent(EditTermActivity.this, CourseEditorActivity.class);
            intent.putExtra(TERM_ID, mId);
            intent.putExtra(COURSE_ID, course.getId());
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());


        mViewModel = new ViewModelProvider(this, factory).get(EditTermViewModel.class);

        mViewModel.mAllData.observe(this, (term) -> {
            if (term != null) {
                mVarTextTitle.setText(state.title == null ? term.getTerm_title() : state.title);
                startDate = state.startDate == null ? term.getTerm_startDate() : getDateWithFormat(state.startDate);
                endDate = state.endDate == null ? term.getTerm_endDate() : getDateWithFormat(state.endDate);
                if (startDate != null) {
                    mStartDate.setText(getDateFormatting(startDate));
                }
                if (endDate != null) {
                    mEndDate.setText(getDateFormatting(endDate));
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_term));
            mActivity = true;
            deleteTermButton.setVisibility(View.GONE);
            mFab.setVisibility(View.GONE);
        } else {
            setTitle(getString(R.string.edit_term));
            mId = extras.getInt(TERM_ID);
            mViewModel.initializeTerm(mId);
            mViewModel.initializeTermCourses(mId);
            mViewModel.getTermCourses().observe(this, (courses) -> mAdapter.submitList(courses));
        }

        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(EditTermActivity.this, CourseEditorActivity.class);
            intent.putExtra(TERM_ID, mId);
            openActivity(intent);
        });
    }

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    protected void showValidationError(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setCancelable(true)
                .setPositiveButton("Okay", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.save_term)
    protected void save() {
        String title = mVarTextTitle.getText().toString();
        String startDate = mStartDate.getText().toString();
        String endDate = mEndDate.getText().toString();
        if (title.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
            showValidationError("Missing fields", "Please enter all fields");
            return;
        }
        mViewModel.saveTerm(title, startDate, endDate);
        Toast.makeText(EditTermActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }


    @OnClick(R.id.delete_term)
    protected void delete() {
        Term term = mViewModel.mAllData.getValue();
        mViewModel.verifyBeforeTermDelete(term,
                () -> {
                    mViewModel.deleteTerm();
                    Toast.makeText(EditTermActivity.this, term.getTerm_title() + " Removed", Toast.LENGTH_SHORT).show();
                    closeActivity();
                }, () -> {
                    showValidationError("Error", term.getTerm_title() + " term cannot be deleted because it has course(s) associated");
                });
    }


    protected int getViewContent() {
        return R.layout.activity_term_editor;
    }


    @OnClick(R.id.term_start_date_value)
    void startDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mStartDate, startDate);
        dateDialog.show(getSupportFragmentManager(), "startDatePicker");
    }


    @OnClick(R.id.term_end_date_value)
    void endDateClickHandler() {
        DialogFragment dateDialog = new DatePickerFragment(mEndDate, endDate);
        dateDialog.show(getSupportFragmentManager(), "endDatePicker");
    }



    private static class State {
        String title;
        String startDate;
        String endDate;
    }

}
