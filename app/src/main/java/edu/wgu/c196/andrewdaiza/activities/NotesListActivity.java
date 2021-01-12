package edu.wgu.c196.andrewdaiza.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.adapters.NoteAdapter;
import edu.wgu.c196.andrewdaiza.viewmodels.ListNotesViewModel;

public class NotesListActivity extends AppCompatActivity {

    public static final String COURSE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.course_editor.COURSE_ID";
    public static final String NOTE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.note_editor.NOTE_ID";

    private ListNotesViewModel mViewModel;
    private NoteAdapter mAdapter;

    protected int mIdEntity;
    protected ViewModelProvider.Factory factory;

    @BindView(R.id.note_list_recycler)
    RecyclerView mRecyclerView;

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
        mAdapter = new NoteAdapter();

        mAdapter.setOnClickListener(note -> {
            Intent intent = new Intent(NotesListActivity.this, NoteEditorActivity.class);
            intent.putExtra(NOTE_ID, note.getId());
            intent.putExtra(COURSE_ID, mIdEntity);
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());


        mViewModel = new ViewModelProvider(this, factory).get(ListNotesViewModel.class);
        Bundle extras = getIntent().getExtras();
        mIdEntity = extras.getInt(COURSE_ID);
        mViewModel.initializeCoursesNotes(mIdEntity);
        mViewModel.getNotesCourse().observe(this, notes -> mAdapter.submitList(notes));

        setTitle("Course Notes");
        FloatingActionButton mFab = findViewById(R.id.fab_add_note);
        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(NotesListActivity.this, NoteEditorActivity.class);
            intent.putExtra(COURSE_ID, mIdEntity);
            openActivity(intent);
        });
    }

    protected int getViewContent() {
        return R.layout.activity_notes_list;
    }


}
