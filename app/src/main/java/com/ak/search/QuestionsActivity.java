package com.ak.search;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ak.search.adapter.MyPagerAdapter;
import com.ak.search.fragment.QuestionFragment;
import com.ak.search.fragment.QuestionReviewFragment;
import com.ak.search.model.Options;
import com.ak.search.model.Questions;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class QuestionsActivity extends AppCompatActivity {


    MyPagerAdapter adapterViewPager;
    long surveyId;
    List<Questions> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            surveyId = getIntent().getExtras().getLong("surveyId");

            questionsList = Questions.find(Questions.class, "surveyid = ?", String.valueOf(surveyId));


            for (int i = 0; i < questionsList.size(); i++) {
                List<Options> opt = Options.find(Options.class, "questionid = ?", String.valueOf(questionsList.get(i).getId()));
                questionsList.get(i).setOptions(opt);
            }
        }


        List<Fragment> fragments = getFragments();
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        //adapterViewPager.
        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(adapterViewPager);

    }

    private List<Fragment> getFragments() {

        List<Fragment> fList = new ArrayList<Fragment>();


        for (int i = 0; i < questionsList.size(); i++) {
            fList.add(QuestionFragment.newInstance(questionsList.get(i)));
        }
        QuestionReviewFragment q = new QuestionReviewFragment();
        Bundle args = new Bundle();
        args.putLong("surveyId", surveyId);
        q.setArguments(args);
        fList.add(q);

        //fList.add(QuestionFragment.newInstance("Fragment 2"));
        //fList.add(QuestionFragment.newInstance("Fragment 3"));


        return fList;

    }

}
