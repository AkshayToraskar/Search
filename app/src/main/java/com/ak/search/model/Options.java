package com.ak.search.model;

import com.orm.SugarRecord;

/**
 * Created by dg hdghfd on 01-12-2016.
 */

public class Options extends SugarRecord {

    String questionid,opt;

    public Options()
    {

    }



    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}
