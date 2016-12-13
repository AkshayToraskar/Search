package com.ak.search.model;

import android.os.Parcelable;
import android.support.design.internal.ParcelableSparseArray;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dg hdghfd on 01-12-2016.
 */

public class Questions extends SugarRecord implements Serializable {

    String surveyid, question;

    @Ignore
    List<Options> options;


    Boolean text, opt;

    public Questions() {
    }

    public List<Options> getOptions(String id) {
        return Options.find(Options.class, "questionid = ?", id);
    }

    public String getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(String surveyid) {
        this.surveyid = surveyid;
    }

    /*public void setOptions(List<Options> options) {
        this.options = options;
    }


    public void setOption(String id)
    {
        options=Options.find(Options.class,"questionid = ?", id);
    }*/


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public Boolean getText() {
        return text;
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public Boolean getOpt() {
        return opt;
    }

    public void setOpt(Boolean opt) {
        this.opt = opt;
    }
}
