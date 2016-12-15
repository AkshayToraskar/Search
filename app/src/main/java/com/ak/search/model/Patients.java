package com.ak.search.model;

import com.orm.SugarRecord;

/**
 * Created by dg hdghfd on 15-12-2016.
 */

public class Patients extends SugarRecord {

    long surveyid;

    String patientname;

    public long getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(long surveyid) {
        this.surveyid = surveyid;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }
}
