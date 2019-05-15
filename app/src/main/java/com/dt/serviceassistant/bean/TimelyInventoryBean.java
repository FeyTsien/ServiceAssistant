package com.dt.serviceassistant.bean;

import java.util.List;

public class TimelyInventoryBean {


    /**
     * total_inventory : 8852.1
     * newly_increased : 1719.58
     * flow_analysis : [{"city":"北京","weight":9162.78},{"city":"广州","weight":6451.116}]
     * customer_inventory : [{"name":"启润","weight":7652.6,"days":12},{"name":"冠杰","weight":2950.5,"days":23}]
     */

    private double total_inventory;
    private double newly_increased;
    private List<FlowAnalysisBean> flow_analysis;
    private List<CustomerInventoryBean> customer_inventory;

    public double getTotal_inventory() {
        return total_inventory;
    }

    public void setTotal_inventory(double total_inventory) {
        this.total_inventory = total_inventory;
    }

    public double getNewly_increased() {
        return newly_increased;
    }

    public void setNewly_increased(double newly_increased) {
        this.newly_increased = newly_increased;
    }

    public List<FlowAnalysisBean> getFlow_analysis() {
        return flow_analysis;
    }

    public void setFlow_analysis(List<FlowAnalysisBean> flow_analysis) {
        this.flow_analysis = flow_analysis;
    }

    public List<CustomerInventoryBean> getCustomer_inventory() {
        return customer_inventory;
    }

    public void setCustomer_inventory(List<CustomerInventoryBean> customer_inventory) {
        this.customer_inventory = customer_inventory;
    }

    public static class FlowAnalysisBean {
        /**
         * city : 北京
         * weight : 9162.78
         */

        private String city;
        private double weight;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    public static class CustomerInventoryBean {
        /**
         * name : 启润
         * weight : 7652.6
         * days : 12
         */

        private String name;
        private double weight;
        private int days;

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

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }
    }
}
