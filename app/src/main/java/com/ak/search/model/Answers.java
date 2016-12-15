package com.ak.search.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by dg hdghfd on 14-12-2016.
 */

public class Answers extends SugarRecord {


    long questionid, patientid;
    int selectedopt;
    String ans;


    public long getQuestionid() {
        return questionid;
    }

    public void setQuestionid(long questionid) {
        this.questionid = questionid;
    }

    public long getPatientid() {
        return patientid;
    }

    public void setPatientid(long patientid) {
        this.patientid = patientid;
    }

    public int getSelectedopt() {
        return selectedopt;
    }

    public void setSelectedopt(int selectedopt) {
        this.selectedopt = selectedopt;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
