package com.ak.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ak.search.adapter.QuestionsAdapter;
import com.ak.search.adapter.SurveyAdapter;
import com.ak.search.app.SessionManager;
import com.ak.search.app.Validate;
import com.ak.search.model.Options;
import com.ak.search.model.Questions;
import com.ak.search.model.Survey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSurveyActivity extends AppCompatActivity {

    @BindView(R.id.txt_survey_name)
    EditText txt_survey_name;

    private List<Questions> questionsList;
    @BindView(R.id.rv_questions)
    RecyclerView recyclerView;
    public static QuestionsAdapter mAdapter;

    Validate validate;

    public static String SURVEYID = "surveyid";
    String surveyId, surveyName;
    public boolean update;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey);
        ButterKnife.bind(this);
        validate = new Validate();
        sessionManager = new SessionManager(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            this.surveyId = String.valueOf(getIntent().getExtras().getLong("surveyId"));
            this.surveyName = getIntent().getExtras().getString("surveyName");
            txt_survey_name.setText(surveyName + " ");
            update = true;
        } else {

            txt_survey_name.setText("Survey " + String.valueOf(sessionManager.getSurveyId()));

            Survey survey = new Survey();

            survey.setName("Survey " + String.valueOf(sessionManager.getSurveyId()));
            long surveyid = survey.save();

            sessionManager.setSurveyId(sessionManager.getSurveyId() + 1);

            this.surveyId = String.valueOf(surveyid);
            update = false;
        }
        questionsList = Questions.find(Questions.class, "surveyid = ?", surveyId);

        List<Options> opt = new ArrayList<>();
        for (int i = 0; i < questionsList.size(); i++) {
            opt = questionsList.get(i).getOptions(String.valueOf(questionsList.get(i).getId()));
        }

        mAdapter = new QuestionsAdapter(this, questionsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    public void onBtnClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_add_survey:

                Intent i = new Intent(this, AddQuestionActivity.class);
                i.putExtra(SURVEYID, surveyId);
                startActivity(i);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_survey, menu);
        if (update) {
            menu.getItem(1).setTitle("update");

        } else {
            menu.getItem(0).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_save_survey:

                if (validate.validateString(txt_survey_name.getText().toString())) {
                    txt_survey_name.setError("Enter Username");
                    break;
                } else {
                    txt_survey_name.setError(null);
                }

                Survey surveyName = Survey.findById(Survey.class, Long.parseLong(surveyId));
                surveyName.setName(txt_survey_name.getText().toString());
                surveyName.save();


                finish();

                break;


            case R.id.action_delete_survey:
                new AlertDialog.Builder(this)
                        .setTitle("Delete")
                        .setMessage("Would you like to delete this survey and Questions?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Survey survey = Survey.findById(Survey.class, Long.parseLong(surveyId));
                                survey.delete();

                                List<Questions> que = Questions.find(Questions.class, "surveyid = ?", surveyId);

                                for (int i = 0; i < que.size(); i++) {
                                    List<Options> op = Options.find(Options.class, "questionid=?", String.valueOf(que.get(i).getId()));

                                    for (int j = 0; j < op.size(); j++) {
                                        op.get(i).delete();
                                    }
                                    que.get(i).delete();
                                }


                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // user doesn't want to logout
                            }
                        })
                        .show();

                break;

            case android.R.id.home:
                SurveyActivity.mAdapter.notifyDataSetChanged();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        questionsList.clear();
        questionsList.addAll(Questions.find(Questions.class, "surveyid = ?", surveyId));
        mAdapter.notifyDataSetChanged();
    }
}
