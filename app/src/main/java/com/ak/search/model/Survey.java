package com.ak.search.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by dg hdghfd on 01-12-2016.
 */

public class Survey  extends SugarRecord {

    String name;
    /*List<Questions> questions;*/

    public Survey(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }*/
}
