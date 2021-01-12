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
import butterknife.OnClick;
import edu.wgu.c196.andrewdaiza.R;
import edu.wgu.c196.andrewdaiza.adapters.TermAdapter;
import edu.wgu.c196.andrewdaiza.viewmodels.ListTermsViewModel;

public class TermsListActivity extends AppCompatActivity {

    public static final String TERM_ID =
            "edu.wgu.c196.andrewdaiza.utilities.term_editor.TERM_ID";


    private ListTermsViewModel mViewModel;

    private TermAdapter mAdapter;

    protected ViewModelProvider.Factory factory;

    @BindView(R.id.recycler_view_terms_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_term)
    FloatingActionButton fabAddTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TermAdapter();

        mAdapter.setOnClickListener(term -> {
            Intent intent = new Intent(TermsListActivity.this, EditTermActivity.class);
            intent.putExtra(TERM_ID, term.getId());
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());


        mViewModel = new ViewModelProvider(this, factory).get(ListTermsViewModel.class);
        mViewModel.getAllTerms().observe(TermsListActivity.this, terms -> mAdapter.submitList(terms));
    }




    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }


    protected void openActivity(Intent intent) {
        startActivity(intent);
    }



    protected void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        openActivity(intent);
    }


    @OnClick(R.id.add_term)
    void addTermClickHandler() {
        openActivity(EditTermActivity.class);
    }
}
