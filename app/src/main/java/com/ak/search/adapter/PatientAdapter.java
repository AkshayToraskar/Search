package com.ak.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ak.search.AddUserActivity;
import com.ak.search.R;
import com.ak.search.ShowSurveyActivity;
import com.ak.search.model.Patients;
import com.ak.search.model.User;

import java.util.List;

/**
 * Created by dg hdghfd on 29-11-2016.
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder> {

private List<Patients> patientsList;
private Context context;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName;

    public MyViewHolder(View view) {
        super(view);
        tvName = (TextView) view.findViewById(R.id.tvName);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Log.v("SurveyID","asf"+surveysList.get(getPosition()).getId());

                Intent i=new Intent(context, ShowSurveyActivity.class);
                i.putExtra("surveyId",patientsList.get(getPosition()).getSurveyid());
                context.startActivity(i);
            }
        });


    }
}


    public PatientAdapter(Context context, List<Patients> patientsList) {
        this.patientsList = patientsList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Patients user = patientsList.get(position);
        holder.tvName.setText(user.getPatientname());
    }

    @Override
    public int getItemCount() {
        return patientsList.size();
    }
}