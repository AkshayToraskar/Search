package com.ak.search.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by dg hdghfd on 14-12-2016.
 */

public class Answers extends SugarRecord {


    String questionid, patientid;
    int selectedopt;
    String ans;




    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
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
