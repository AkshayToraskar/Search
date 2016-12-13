package com.ak.search.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ak.search.R;
import com.ak.search.model.Questions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by dg hdghfd on 08-12-2016.
 */

public class QuestionFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    @BindView(R.id.rg_options)
    RadioGroup rg_option;

    @BindView(R.id.rb_opt1)
    RadioButton rb_opt1;
    @BindView(R.id.rb_opt2)
    RadioButton rb_opt2;

    @BindView(R.id.txt_answer)
    EditText et_answer;

    @BindView(R.id.tv_question)
    TextView tv_question;

    public static final QuestionFragment newInstance(Questions message)
    {
        QuestionFragment f = new QuestionFragment();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this,v);

        Questions message = (Questions) getArguments().getSerializable(EXTRA_MESSAGE);

        tv_question.setText(message.getQuestion());


        if (!message.getOpt()) {
            rg_option.setVisibility(GONE);
        } else {
            if(message.getOptions().size()>1) {
                rb_opt1.setText(message.getOptions().get(0).getOpt());
                rb_opt2.setText(message.getOptions().get(1).getOpt());
            }
            /*if (opt.size() > 2) {
                for (int i = 2; i < opt.size(); i++) {

                }
            }*/

            if(message.getOptions().size()>0){
                rb_opt1.setText(message.getOptions().get(0).getOpt());
            }else if(message.getOptions().size()>1){
                rb_opt1.setText(message.getOptions().get(0).getOpt());
                rb_opt2.setText(message.getOptions().get(1).getOpt());
            }
            if (message.getOptions().size() > 2) {
                for(int i=2; i<message.getOptions().size(); i++) {
                    RadioButton rb = new RadioButton(getContext());
                    rb.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    rb.setText(message.getOptions().get(i).getOpt());
                    rg_option.addView(rb);
                    //allEds.add(text);
                }
            }

        }

        if (!message.getText()) {
            et_answer.setVisibility(GONE);
        }


        return v;

    }
}