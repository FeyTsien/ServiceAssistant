package com.dt.serviceassistant.bean;

import java.util.List;

public class StaffAnalysisBean {


    /**
     * annual_cumulative : 14234.79
     * month_cumulative : 2717.42
     * annual_ranking : [{"name":"刘亚男","weight":5970.4},{"name":"王震","weight":3138.3}]
     * month_ranking : [{"name":"刘亚男","weight":2019.2},{"name":"王震","weight":1204.25}]
     * monthly_statistics : [{"staff_name":"刘亚男"," workload":[{"month":"2019-01","weight":9724.707},{"month":"2019-02","weight":4924.8},{"month":"2019-03","weight":3538.23},{"month":"2019-04","weight":8602.678},{"month":"2019-05","weight":4245.6}]},{"staff_name":"王震"," workload":[{"month":"2019-01","weight":4312.566},{"month":"2019-02","weight":1001.416},{"month":"2019-03","weight":4260.34},{"month":"2019-04","weight":5503.538},{"month":"2019-05","weight":2632.6}]}]
     */

    private double annual_cumulative;
    private double month_cumulative;
    private List<AnnualRankingBean> annual_ranking;
    private List<MonthRankingBean> month_ranking;
    private List<MonthlyStatisticsBean> monthly_statistics;

    public double getAnnual_cumulative() {
        return annual_cumulative;
    }

    public void setAnnual_cumulative(double annual_cumulative) {
        this.annual_cumulative = annual_cumulative;
    }

    public double getMonth_cumulative() {
        return month_cumulative;
    }

    public void setMonth_cumulative(double month_cumulative) {
        this.month_cumulative = month_cumulative;
    }

    public List<AnnualRankingBean> getAnnual_ranking() {
        return annual_ranking;
    }

    public void setAnnual_ranking(List<AnnualRankingBean> annual_ranking) {
        this.annual_ranking = annual_ranking;
    }

    public List<MonthRankingBean> getMonth_ranking() {
        return month_ranking;
    }

    public void setMonth_ranking(List<MonthRankingBean> month_ranking) {
        this.month_ranking = month_ranking;
    }

    public List<MonthlyStatisticsBean> getMonthly_statistics() {
        return monthly_statistics;
    }

    public void setMonthly_statistics(List<MonthlyStatisticsBean> monthly_statistics) {
        this.monthly_statistics = monthly_statistics;
    }

    public static class AnnualRankingBean {
        /**
         * name : 刘亚男
         * weight : 5970.4
         */

        private String name;
        private double weight;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    public static class MonthRankingBean {
        /**
         * name : 刘亚男
         * weight : 2019.2
         */

        private String name;
        private double weight;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    public static class MonthlyStatisticsBean {
        /**
         * staff_name : 刘亚男
         *  workload : [{"month":"2019-01","weight":9724.707},{"month":"2019-02","weight":4924.8},{"month":"2019-03","weight":3538.23},{"month":"2019-04","weight":8602.678},{"month":"2019-05","weight":4245.6}]
         */

        private String staff_name;
        private List<workloadBean> workload;

        public String getStaff_name() {
            return staff_name;
        }

        public void setStaff_name(String staff_name) {
            this.staff_name = staff_name;
        }

        public List<workloadBean> getWorkload() {
            return workload;
        }

        public void setWorkload(List<workloadBean> workload) {
            this.workload = workload;
        }

        public static class workloadBean {
            /**
             * month : 2019-01
             * weight : 9724.707
             */

            private String month;
            private double weight;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public double getWeight() {
                return weight;
            }

            public void setWeight(double weight) {
                this.weight = weight;
            }
        }
    }
}
