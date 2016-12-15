package com.ak.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ak.search.adapter.GetQuestionsAdapter;
import com.ak.search.adapter.GetSurveyAdapter;
import com.ak.search.adapter.PatientAdapter;
import com.ak.search.adapter.QuestionsAdapter;
import com.ak.search.app.Validate;
import com.ak.search.model.Options;
import com.ak.search.model.Patients;
import com.ak.search.model.Questions;
import com.ak.search.model.Survey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetSurveyActivity extends AppCompatActivity {

    long surveyId;
    private List<Patients> patientList;
    @BindView(R.id.rv_questions)
    RecyclerView recyclerView;
    public PatientAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_survey);
        ButterKnife.bind(this);
        patientList = new ArrayList<>();

       /* if (getIntent().getExtras() != null) {
            surveyId = getIntent().getExtras().getLong("surveyId");

            questionsList = Questions.find(Questions.class, "surveyid = ?", String.valueOf(surveyId));

            List<Options> opt = new ArrayList<>();
            for (int i = 0; i < questionsList.size(); i++) {
                opt = questionsList.get(i).getOptions(String.valueOf(questionsList.get(i).getId()));
            }*/

        patientList = Patients.listAll(Patients.class);

            mAdapter = new PatientAdapter(this, patientList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

         /*   Log.v("GET SURVEY", "" + surveyId);

        }*/

    }
}
