package com.ak.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ak.search.AddSurveyActivity;
import com.ak.search.AddUserActivity;
import com.ak.search.R;
import com.ak.search.model.User;

import java.util.List;

/**
 * Created by dg hdghfd on 29-11-2016.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

private List<User> userList;
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

                Intent i=new Intent(context, AddUserActivity.class);
                i.putExtra("userId",userList.get(getPosition()).getId());
                context.startActivity(i);
            }
        });


    }
}


    public UsersAdapter(Context context, List<User> userList) {
        this.userList = userList;
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
        User user = userList.get(position);
        holder.tvName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}