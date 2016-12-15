package com.ak.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ak.search.adapter.GetQuestionsAdapter;
import com.ak.search.model.Answers;
import com.ak.search.model.Options;
import com.ak.search.model.Questions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowSurveyActivity extends AppCompatActivity {

    long surveyId;
    private List<Questions> questionsList;
    @BindView(R.id.rv_questions)
    RecyclerView recyclerView;
    public GetQuestionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_survey);
        ButterKnife.bind(this);

        if(getIntent().getExtras()!=null){
            surveyId = getIntent().getExtras().getLong("surveyId");


            mAdapter = new GetQuestionsAdapter(this, getQuestionList());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            Log.v("GET SURVEY", "" + surveyId);
        }

    }


    public List<Questions> getQuestionList() {


        questionsList = Questions.find(Questions.class, "surveyid = ?", String.valueOf(surveyId));

        ///List<Options> opt = new ArrayList<>();
        for (int i = 0; i < questionsList.size(); i++) {
            //opt = questionsList.get(i).getOptions(String.valueOf(questionsList.get(i).getId()));
            List<Options> opt = Options.find(Options.class, "questionid = ?", String.valueOf(questionsList.get(i).getId()));
            questionsList.get(i).setOptions(opt);


            List<Answers> answers = Answers.find(Answers.class, "questionId = ?", String.valueOf(questionsList.get(i).getId()));
            questionsList.get(i).setAnswers(answers.get(0));

        }



        return questionsList;
    }

}
