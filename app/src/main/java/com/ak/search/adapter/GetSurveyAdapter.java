package com.ak.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ak.search.AddSurveyActivity;
import com.ak.search.MainActivity;
import com.ak.search.R;
import com.ak.search.model.Survey;

import java.util.List;

/**
 * Created by dg hdghfd on 29-11-2016.
 */

public class GetSurveyAdapter extends RecyclerView.Adapter<GetSurveyAdapter.MyViewHolder> {

    private List<Survey> surveysList;
    private Context context;
    private RadioButton lastCheckedRB = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton rbSurveyName;

        public MyViewHolder(final View view) {
            super(view);
            rbSurveyName = (RadioButton) view.findViewById(R.id.rb_survey_name);


            rbSurveyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lastCheckedRB != null) {
                        lastCheckedRB.setChecked(false);
                    }
                    lastCheckedRB = rbSurveyName;
                    MainActivity.surveyId=surveysList.get(getPosition()).getId();
                }
            });





        }
    }


    public GetSurveyAdapter(Context context, List<Survey> surveysList) {
        this.surveysList = surveysList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.survey_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Survey survey = surveysList.get(position);
        holder.rbSurveyName.setText(survey.getName());

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return surveysList.size();
    }
}