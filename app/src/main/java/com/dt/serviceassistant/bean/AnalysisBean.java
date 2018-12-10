package com.dt.serviceassistant.bean;

import java.util.List;

/**
 * Created by admin on 2018/7/2.
 */

public class AnalysisBean {
    private int start;
    private List<String> analysis;
    private String analysissingle;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<String> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<String> analysis) {
        this.analysis = analysis;
    }

    public String getAnalysissingle() {
        return analysissingle;
    }

    public void setAnalysissingle(String analysissingle) {
        this.analysissingle = analysissingle;
    }

}
