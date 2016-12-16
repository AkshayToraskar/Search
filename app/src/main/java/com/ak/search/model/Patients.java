package com.ak.search.model;

import com.orm.SugarRecord;

/**
 * Created by dg hdghfd on 15-12-2016.
 */

public class Patients extends SugarRecord {


    String patientname,surveyid;

    public String getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(String surveyid) {
        this.surveyid = surveyid;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }
}
