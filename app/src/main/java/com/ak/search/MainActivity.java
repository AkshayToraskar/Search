package com.ak.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ak.search.adapter.GetSurveyAdapter;
import com.ak.search.adapter.SurveyAdapter;
import com.ak.search.app.SessionManager;
import com.ak.search.app.Validate;
import com.ak.search.model.Questions;
import com.ak.search.model.Survey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private String username;
    private boolean isAdmin;
    public static String TAG = MainActivity.class.getName();
    private SessionManager sessionManager;

    @BindView(R.id.ll_admin)
    LinearLayout llAdmin;

    @BindView(R.id.ll_user)
    LinearLayout llUser;

    @BindView(R.id.txt_patient_name)
    EditText txt_patient_name;

    private List<Survey> surveysList;
    @BindView(R.id.rv_survey)
    RecyclerView recyclerView;
    public GetSurveyAdapter mAdapter;
    private Validate validate;

    public static long surveyId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
        validate = new Validate();

        if (sessionManager.isLoggedIn()) {
            username = sessionManager.getUsername();
            isAdmin = sessionManager.isAdmin();

            if (isAdmin) {
                Log.v(TAG, "ADMIN");
                llUser.setVisibility(View.GONE);
            } else {
                Log.v(TAG, "USER");
                llAdmin.setVisibility(View.GONE);

                surveysList = Survey.listAll(Survey.class);

                mAdapter = new GetSurveyAdapter(this, surveysList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(isAdmin){
            menu.getItem(0).setVisible(false);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_logout:

                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Would you like to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sessionManager.setLogin(false,"",false);
                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // user doesn't want to logout
                            }
                        })
                        .show();
                break;

            case R.id.action_patient_data:
                startActivity(new Intent(this,GetSurveyActivity.class));
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void onBtnClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_user:
                startActivity(new Intent(this, UserActivity.class));
                break;

            case R.id.btn_survey:
                startActivity(new Intent(this, SurveyActivity.class));
                break;

            case R.id.btn_data:
                startActivity(new Intent(this,GetSurveyActivity.class));
                break;

            case R.id.btn_start_survey:

                if (validate.validateString(txt_patient_name.getText().toString())) {
                    txt_patient_name.setError("Enter Patient Name");
                    return;
                }else {
                    txt_patient_name.setError(null);
                }

                if(surveyId==-1){
                    Toast.makeText(getApplicationContext(),"Survey is not selected",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Intent i=new Intent(this, GetSurveyActivity.class);


                Intent i=new Intent(this, QuestionsActivity.class);
                i.putExtra("surveyId",surveyId);
                i.putExtra("patientName",txt_patient_name.getText().toString());
                startActivity(i);

                break;


        }

    }
}
