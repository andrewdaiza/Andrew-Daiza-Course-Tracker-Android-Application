package edu.wgu.c196.andrewdaiza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.database.entities.Mentor;
import edu.wgu.c196.andrewdaiza.viewmodels.EditMentorViewModel;


public class MentorEditorActivity extends AppCompatActivity {

    public static final String COURSE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.course_editor.COURSE_ID";
    public static final String MENTOR_ID =
            "edu.wgu.c196.andrewdaiza.utilities.mentor_editor.MENTOR_ID";

    protected boolean mActivity;
    protected int mId;
    protected int mIdEntity;
    protected ViewModelProvider.Factory factory;

    private EditMentorViewModel mViewModel;
    private final MentorState state = new MentorState();

    @BindView(R.id.first_name_mentor_text)
    protected EditText mFirstName;
    @BindView(R.id.last_name_mentor_text)
    protected EditText mLastName;
    @BindView(R.id.phone_mentor_text)
    protected EditText mPhone;
    @BindView(R.id.email_mentor_text)
    protected EditText mEmail;
    @BindView(R.id.delete_mentor)
    protected Button deleteMentor;

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

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());

        mViewModel = new ViewModelProvider(this, factory).get(EditMentorViewModel.class);
        mViewModel.mLiveMentor.observe(this, (mentor) -> {
            if (mentor != null) {
                mFirstName.setText(state.firstName == null ? mentor.getMentor_firstName() : state.firstName);
                mLastName.setText(state.lastName == null ? mentor.getMentor_lastName() : state.lastName);
                mPhone.setText(state.phoneNumber == null ? mentor.getMentor_phoneNumber() : state.phoneNumber);
                mEmail.setText(state.email == null ? mentor.getMentor_email() : state.email);
            }
        });

        Bundle extras = getIntent().getExtras();
        mIdEntity = extras.getInt(COURSE_ID);

        if (extras.getInt(MENTOR_ID) == 0) {
            setTitle("New Mentor");
            deleteMentor.setVisibility(View.GONE);
            mActivity = true;
        } else {
            setTitle("Edit Mentor");
            mId = extras.getInt(MENTOR_ID);
            mViewModel.initializeMentor(mId);
        }
    }


    protected int getViewContent() {
        return R.layout.activity_mentor_editor;
    }


    protected void showValidationError(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setCancelable(true)
                .setPositiveButton("Okay", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.save_mentor)
    protected void save() {
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String phone = mPhone.getText().toString();
        String email = mEmail.getText().toString();
        int courseId = mIdEntity;

        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty()) {
            showValidationError("Error", "Please enter all fields");
            return;
        }
        mViewModel.saveMentor(courseId, firstName, lastName, phone, email);
        Toast.makeText(MentorEditorActivity.this, firstName + " " + lastName + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @OnClick(R.id.delete_mentor)
    protected void delete() {
        Mentor mentor = mViewModel.mLiveMentor.getValue();
        String mentorName = mentor.getMentor_firstName() + " " + mentor.getMentor_lastName();
        mViewModel.deleteMentor();
        String text = mentorName + " Deleted";
        Toast.makeText(MentorEditorActivity.this, text, Toast.LENGTH_SHORT).show();
        closeActivity();
    }



    private static class MentorState {
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
    }
}
