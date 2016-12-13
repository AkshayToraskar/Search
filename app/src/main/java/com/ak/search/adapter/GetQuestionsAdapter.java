package com.ak.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ak.search.AddQuestionActivity;
import com.ak.search.R;
import com.ak.search.model.Options;
import com.ak.search.model.Questions;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by dg hdghfd on 29-11-2016.
 */

public class GetQuestionsAdapter extends RecyclerView.Adapter<GetQuestionsAdapter.MyViewHolder> {

    private List<Questions> questionsList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuestion;
        public EditText etAnswer;
        public RadioGroup rgOption;
        public RadioButton rbOpt1, rbOpt2;

        public MyViewHolder(View view) {
            super(view);
            tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
            etAnswer = (EditText) view.findViewById(R.id.txt_answer);
            rgOption = (RadioGroup) view.findViewById(R.id.rg_options);
            rbOpt1 = (RadioButton) view.findViewById(R.id.rb_opt1);
            rbOpt2 = (RadioButton) view.findViewById(R.id.rb_opt2);



            //List<Options> opt = Options.find(Options.class, "questionid = ?", String.valueOf());
/*
            if(opt.size()>0){
                rbOpt1.setText(opt.get(0).getOpt());
            }else if(opt.size()>1){
                rbOpt1.setText(opt.get(0).getOpt());
                rbOpt2.setText(opt.get(1).getOpt());
            }
            if (opt.size() > 2) {
                for(int i=1; i<opt.size(); i++) {
                    RadioButton rb = new RadioButton(context);
                    rb.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    rb.setText(opt.get(i).getOpt());
                    rgOption.addView(rb);
                    //allEds.add(text);
                }
            }*/

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context, AddQuestionActivity.class);
                    i.putExtra("questionId", questionsList.get(getPosition()).getId());
                    context.startActivity(i);
                }
            });*/


        }
    }


    public GetQuestionsAdapter(Context context, List<Questions> questionsList) {
        this.questionsList = questionsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questions_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Questions questions = questionsList.get(position);
        holder.tvQuestion.setText(questions.getQuestion());

        Log.v("asdff a",""+questionsList.get(position).getId());


        List<Options> opt = Options.find(Options.class, "questionid = ?", String.valueOf(questionsList.get(position).getId()));





        if (!questions.getOpt()) {
            holder.rgOption.setVisibility(GONE);
        } else {
            if(opt.size()>1) {
                holder.rbOpt1.setText(opt.get(0).getOpt());
                holder.rbOpt2.setText(opt.get(1).getOpt());
            }
            /*if (opt.size() > 2) {
                for (int i = 2; i < opt.size(); i++) {

                }
            }*/

            if(opt.size()>0){
                holder.rbOpt1.setText(opt.get(0).getOpt());
            }else if(opt.size()>1){
                holder.rbOpt1.setText(opt.get(0).getOpt());
                holder.rbOpt2.setText(opt.get(1).getOpt());
            }
            if (opt.size() > 2) {
                for(int i=2; i<opt.size(); i++) {
                    RadioButton rb = new RadioButton(context);
                    rb.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    rb.setText(opt.get(i).getOpt());
                    holder.rgOption.addView(rb);
                    //allEds.add(text);
                }
            }

        }

        if (!questions.getText()) {
            holder.etAnswer.setVisibility(GONE);
        }

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }
}