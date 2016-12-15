package com.ak.search;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.search.adapter.MyPagerAdapter;
import com.ak.search.app.SaveAnswer;
import com.ak.search.fragment.QuestionFragment;
import com.ak.search.fragment.QuestionReviewFragment;
import com.ak.search.model.Answers;
import com.ak.search.model.Options;
import com.ak.search.model.Patients;
import com.ak.search.model.Questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsActivity extends AppCompatActivity implements SaveAnswer {


    MyPagerAdapter adapterViewPager;
    long surveyId, patientId;
    String patientName;

    List<Questions> questionsList;
    List<Fragment> fragments;

    @BindView(R.id.view_pager)
    ViewPager pager;

    @BindView(R.id.btn_next)
    Button btn_next;

    @BindView(R.id.btn_previous)
    Button btn_previous;

    @BindView(R.id.txt_page_count)
    TextView txt_page_count;
    QuestionReviewFragment q;


    public static HashMap<Long, Answers> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);

        answers = new HashMap<>();

        if (getIntent().getExtras() != null) {
            surveyId = getIntent().getExtras().getLong("surveyId");
            patientName = getIntent().getExtras().getString("patientName");

            Patients patients = new Patients();
            patients.setPatientname(patientName);
            patients.setSurveyid(surveyId);
            patientId = patients.save();

            questionsList = Questions.find(Questions.class, "surveyid = ?", String.valueOf(surveyId));


            for (int i = 0; i < questionsList.size(); i++) {
                List<Options> opt = Options.find(Options.class, "questionid = ?", String.valueOf(questionsList.get(i).getId()));
                questionsList.get(i).setOptions(opt);
            }
        }


        fragments = getFragments();
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setOffscreenPageLimit(fragments.size());
        pager.setAdapter(adapterViewPager);
        pager.addOnPageChangeListener(viewPagerPageChangeListener);
        txt_page_count.setText(pager.getCurrentItem() + 1 + "/" + (fragments.size() - 1));

    }


    public void onBtnClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_next:

                if (btn_next.getText().toString().equalsIgnoreCase("next")) {
                    int current = getItem(+1);
                    if (current < fragments.size()) {
                        // move to next screen
                        pager.setCurrentItem(current);
                    } else {
                        // launchHomeScreen();

                        for (Map.Entry m : answers.entrySet()) {
                            Answers an = (Answers) m.getValue();
                            // an.save();

                            Log.v("ans ", " " + m.getKey() + "," + an.getPatientid() + "," + an.getPatientid() + "," + an.getAns());

                        }


                    }
                } else {

                   /* for(int i=0; i<fragments.size();i++){
                        Log.v("ans ",answers.get(i).getQuestionId()+", ");
                    }*/

                    //Log.v("ans "," "+answers.get(0).getQuestionId());

                    for (Map.Entry m : answers.entrySet()) {
                        Answers an = (Answers) m.getValue();
                        an.save();
                        Log.v("ans ", " " + m.getKey() + "," + "," + an.getPatientid() + "--------" + an.getSelectedopt() + "," + an.getAns());

                    }

                    Toast.makeText(getApplicationContext(), "DONE", Toast.LENGTH_SHORT).show();

                    finish();
                }

                break;

            case R.id.btn_previous:
                int current1 = getItem(-1);
                if (current1 < fragments.size()) {
                    // move to previous screen
                    pager.setCurrentItem(current1);
                } else {
                    // launchHomeScreen();
                }
                break;
        }
    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            //addBottomDots(position);

            Log.v("aa", "page Selected " + position);


            //QuestionFragment qf=new QuestionFragment();
            //answers.put(position,qf.getAns());


            int currentPage = pager.getCurrentItem();

            txt_page_count.setText(currentPage + 1 + "/" + (fragments.size() - 1));


            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == fragments.size() - 1) {
                // last page. make button text to GOT IT
                btn_next.setText("DONE");

                txt_page_count.setVisibility(View.INVISIBLE);
                //btn_next.setVisibility(View.VISIBLE);
                btn_previous.setVisibility(View.VISIBLE);
            } else if (position == 0) {
                // still pages are left
                // btn_next.setText(getString(R.string.next));

                btn_next.setText("NEXT");

                txt_page_count.setVisibility(View.VISIBLE);
                //btn_next.setVisibility(View.VISIBLE);
                btn_previous.setVisibility(View.INVISIBLE);
            } else {

                btn_next.setText("NEXT");
                txt_page_count.setVisibility(View.VISIBLE);
                //btn_next.setVisibility(View.VISIBLE);
                btn_previous.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            //Log.v("aa", "page Scrolled");
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            //  Log.v("aa", "page Scroll state changed "+arg0);
        }
    };


    private List<Fragment> getFragments() {

        List<Fragment> fList = new ArrayList<Fragment>();


        for (int i = 0; i < questionsList.size(); i++) {
            fList.add(QuestionFragment.newInstance(questionsList.get(i)));
        }
        q = new QuestionReviewFragment();
        Bundle args = new Bundle();
        args.putLong("surveyId", surveyId);
        q.setArguments(args);
        fList.add(q);

        //fList.add(QuestionFragment.newInstance("Fragment 2"));
        //fList.add(QuestionFragment.newInstance("Fragment 3"));


        return fList;

    }

    @Override
    public void onAnswerSave(Answers ans) {

        ans.setPatientid(patientId);
        answers.put(ans.getQuestionid(), ans);

        if (q.updateReviewAnswer != null) {
            q.updateReviewAnswer.onReviewUpdate(answers);
        }

        //Log.v("asdf",""+ans.getAns());
    }
}
