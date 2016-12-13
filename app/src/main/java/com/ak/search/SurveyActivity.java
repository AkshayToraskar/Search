package com.ak.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ak.search.adapter.SurveyAdapter;
import com.ak.search.adapter.UsersAdapter;
import com.ak.search.model.Survey;
import com.ak.search.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurveyActivity extends AppCompatActivity {

    private List<Survey> surveysList;
    @BindView(R.id.rv_survey)
    RecyclerView recyclerView;
    public static SurveyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        surveysList = Survey.listAll(Survey.class);

        mAdapter = new SurveyAdapter(this, surveysList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SurveyActivity.this, AddSurveyActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        surveysList.clear();
        surveysList.addAll(Survey.listAll(Survey.class));
        mAdapter.notifyDataSetChanged();
    }

}
