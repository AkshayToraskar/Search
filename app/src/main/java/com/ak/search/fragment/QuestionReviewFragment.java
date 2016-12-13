package com.ak.search.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ak.search.R;
import com.ak.search.adapter.GetQuestionsAdapter;
import com.ak.search.model.Options;
import com.ak.search.model.Questions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionReviewFragment extends Fragment {

    long surveyId;
    private List<Questions> questionsList;
    @BindView(R.id.rv_questions)
    RecyclerView recyclerView;
    public GetQuestionsAdapter mAdapter;

    public QuestionReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_question_review, container, false);

        ButterKnife.bind(this,view);
        questionsList = new ArrayList<>();



        if (getArguments() != null) {
            surveyId = getArguments().getLong("surveyId");

            questionsList = Questions.find(Questions.class, "surveyid = ?", String.valueOf(surveyId));

            List<Options> opt = new ArrayList<>();
            for (int i = 0; i < questionsList.size(); i++) {
                opt = questionsList.get(i).getOptions(String.valueOf(questionsList.get(i).getId()));
            }

            mAdapter = new GetQuestionsAdapter(getContext(), questionsList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

            Log.v("GET SURVEY", "" + surveyId);

        }

        return view;

    }

}
