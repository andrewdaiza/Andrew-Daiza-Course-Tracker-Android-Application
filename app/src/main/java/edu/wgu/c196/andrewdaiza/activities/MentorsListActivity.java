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
import edu.wgu.c196.andrewdaiza.adapters.MentorAdapter;
import edu.wgu.c196.andrewdaiza.viewmodels.ListMentorsViewModel;


public class MentorsListActivity extends AppCompatActivity {

    public static final String COURSE_ID =
            "edu.wgu.c196.andrewdaiza.utilities.course_editor.COURSE_ID";
    public static final String MENTOR_ID =
            "edu.wgu.c196.andrewdaiza.utilities.mentor_editor.MENTOR_ID";


    protected int mIdEntity;
    protected ViewModelProvider.Factory factory;

    private ListMentorsViewModel mViewModel;

    private MentorAdapter mAdapter;

    @BindView(R.id.mentor_list_recycler)
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
        setContentView(R.layout.activity_mentors_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MentorAdapter();

        mAdapter.setOnClickListener(mentor -> {
            Intent intent = new Intent(MentorsListActivity.this, MentorEditorActivity.class);
            intent.putExtra(MENTOR_ID, mentor.getId());
            intent.putExtra(COURSE_ID, mIdEntity);
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());


        mViewModel = new ViewModelProvider(this, factory).get(ListMentorsViewModel.class);
        Bundle extras = getIntent().getExtras();
        mIdEntity = extras.getInt(COURSE_ID);
        mViewModel.initializeCourseMentors(mIdEntity);
        mViewModel.getCourseMentors().observe(this, mentors -> mAdapter.submitList(mentors));

        setTitle("Mentors");
        FloatingActionButton fab = findViewById(R.id.add_mentor);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MentorsListActivity.this, MentorEditorActivity.class);
            intent.putExtra(COURSE_ID, mIdEntity);
            openActivity(intent);
        });
    }

}
