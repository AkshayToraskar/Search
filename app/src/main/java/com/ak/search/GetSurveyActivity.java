package com.ak.search;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ak.search.adapter.GetQuestionsAdapter;
import com.ak.search.adapter.GetSurveyAdapter;
import com.ak.search.adapter.PatientAdapter;
import com.ak.search.adapter.QuestionsAdapter;
import com.ak.search.app.CSVUtils;
import com.ak.search.app.Validate;
import com.ak.search.model.Answers;
import com.ak.search.model.Options;
import com.ak.search.model.Patients;
import com.ak.search.model.Questions;
import com.ak.search.model.Survey;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetSurveyActivity extends AppCompatActivity {

    long surveyId;
    private List<Patients> patientList;
    @BindView(R.id.rv_questions)
    RecyclerView recyclerView;

    @BindView(R.id.spnSurveyName)
    Spinner spnSurveyName;

    public PatientAdapter mAdapter;
    ArrayAdapter<String> spnSurveyNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_survey);
        ButterKnife.bind(this);
        patientList = new ArrayList<>();

        getSupportActionBar().setTitle("Patient List");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* if (getIntent().getExtras() != null) {
            surveyId = getIntent().getExtras().getLong("surveyId");

            questionsList = Questions.find(Questions.class, "surveyid = ?", String.valueOf(surveyId));

            List<Options> opt = new ArrayList<>();
            for (int i = 0; i < questionsList.size(); i++) {
                opt = questionsList.get(i).getOptions(String.valueOf(questionsList.get(i).getId()));
            }*/

        patientList = Patients.listAll(Patients.class);

        List<Survey> surveyList = Survey.listAll(Survey.class);
        final String surveyName[] = new String[surveyList.size() + 1];
        surveyName[0] = "All";
        for (int i = 0; i < surveyList.size(); i++) {
            surveyName[i + 1] = surveyList.get(i).getName();
        }


        spnSurveyNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, surveyName);
        spnSurveyNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnSurveyName.setAdapter(spnSurveyNameAdapter);


        mAdapter = new PatientAdapter(this, patientList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        spnSurveyName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                        String selectedItem = String.valueOf(spnSurveyName.getSelectedItem());

                                                        if (selectedItem.equals("All")) {
                                                            patientList.clear();
                                                            patientList.addAll(Patients.listAll(Patients.class));
                                                            mAdapter.notifyDataSetChanged();
                                                        } else {
                                                            List<Survey> selectedSurvey = Survey.find(Survey.class, "name = ?", selectedItem);

                                                            if (selectedSurvey.size() > 0) {
                                                                String surveyId1 = String.valueOf(selectedSurvey.get(0).getId());
                                                                patientList.clear();
                                                                patientList.addAll(Patients.find(Patients.class, "surveyid=?", surveyId1));
                                                                mAdapter.notifyDataSetChanged();
                                                            }
                                                        }

                                                    }


                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                                    }
                                                }

        );




         /*   Log.v("GET SURVEY", "" + surveyId);

        }*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_export:
                generateCSV();
                break;
        }


        return super.onOptionsItemSelected(item);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_get_survey, menu);


        return true;
    }

    public void generateCSV() {

        try {



            File myDirectory = new File(Environment.getExternalStorageDirectory(), "SEARCH");

            if(!myDirectory.exists()) {
                myDirectory.mkdirs();
            }

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());


            String csv = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator +"SEARCH"+ File.separator +"PatientData "+currentDateTimeString+".csv";
            CSVWriter writer = null;
            writer = new CSVWriter(new FileWriter(csv));

            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[]{"India", "New Delhi"});
            data.add(new String[]{"United States", "Washington D.C"});
            data.add(new String[]{"Germany", "Berlin"});
            data.add(new String[]{"asdf", "23423s"});
            data.add(new String[]{"Germ3423any", "asdf"});
            writer.writeAll(data);
            writer.close();

            Log.v("asdf","SUCCESS");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
