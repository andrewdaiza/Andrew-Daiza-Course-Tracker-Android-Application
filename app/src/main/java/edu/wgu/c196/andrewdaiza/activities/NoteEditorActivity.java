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
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.database.entities.Note;
import edu.wgu.c196.andrewdaiza.viewmodels.EditNoteViewModel;


public class NoteEditorActivity extends AppCompatActivity {

    public static final String COURSE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.course_editor.COURSE_ID";
    public static final String NOTE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.note_editor.NOTE_ID";
    private EditNoteViewModel mViewModel;
    private final State state = new State();

    @BindView(R.id.note_title_edit)
    EditText mVarTextTitle;
    @BindView(R.id.note_description_edit)
    EditText mDescription;
    @BindView(R.id.share_note)
    Button mSendEmail;
    @BindView(R.id.delete_note)
    protected Button deleteNoteButton;

    protected boolean mActivity;
    protected int mId;
    protected int mIdEntity;
    protected ViewModelProvider.Factory factory;

    private void loadState(String title, String description) {
        mVarTextTitle.setText(title);
        mDescription.setText(description);
    }

    protected int getViewContent() {
        return R.layout.activity_note_editor;
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

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());

        mViewModel = new ViewModelProvider(this, factory).get(EditNoteViewModel.class);
        mViewModel.mAllNotes.observe(this, (note) -> {
            if (note != null) {
                mVarTextTitle.setText(state.title == null ? note.getNote_title() : state.title);
                mDescription.setText(state.description == null ? note.getNote_description() : state.description);
            }
        });

        Bundle extras = getIntent().getExtras();
        mIdEntity = extras.getInt(COURSE_ID);

        if (extras.getInt(NOTE_ID) == 0) {
            setTitle("New Note");
            mActivity = true;
            deleteNoteButton.setVisibility(View.GONE);
            mSendEmail.setVisibility(View.GONE);
        } else {
            setTitle("Edit Note");
            mId = extras.getInt(NOTE_ID);
            mViewModel.initializeNotes(mId);
        }
    }


    protected void showValidationError(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setCancelable(true)
                .setPositiveButton("Okay", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.save_note)
    protected void save() {
        String title = String.valueOf(mVarTextTitle.getText());
        String description = String.valueOf( mDescription.getText());
        int courseId = mIdEntity;

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            showValidationError("Error", "Please enter all fields");
            return;
        }
        mViewModel.saveNote(courseId, title, description);
        Toast.makeText(NoteEditorActivity.this, title + " saved", Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    @OnClick(R.id.delete_note)
    protected void delete() {
        Note course = mViewModel.mAllNotes.getValue();
        String title = course.getNote_title();
        mViewModel.deleteNote();
        String text = title + " Deleted";
        Toast.makeText(NoteEditorActivity.this, text, Toast.LENGTH_SHORT).show();
        closeActivity();
    }

    protected RecyclerView getRecyclerView() {
        return null;
    }


    @OnClick(R.id.share_note)
    public void sendMail() {
            String subject = String.valueOf(mVarTextTitle.getText());
            String message = String.valueOf(mDescription.getText());

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Pick application to share with"));

    }

    private static class State {
        String title;
        String description;
    }
}
