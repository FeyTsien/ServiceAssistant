package com.dt.serviceassistant.bean;

import java.util.List;

/**
 * Created by admin on 2018/7/2.
 */

public class AnalysisBean {
    private int start;
    private List<String> analysis;
    private String analysissingle;
    private String updatetime;
    private String amount;
    private String totalshipment;
    private String paid;
    private String unpaid;
    private List<RankingBean> ranking;


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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalshipment() {
        return totalshipment;
    }

    public void setTotalshipment(String totalshipment) {
        this.totalshipment = totalshipment;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(String unpaid) {
        this.unpaid = unpaid;
    }

    public List<RankingBean> getRanking() {
        return ranking;
    }

    public void setRanking(List<RankingBean> ranking) {
        this.ranking = ranking;
    }

    public static class RankingBean {
        /**
         * client : 客户1
         * money : 5000万
         */

        private String client;
        private String money;
        private String days;

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }
    }
}
