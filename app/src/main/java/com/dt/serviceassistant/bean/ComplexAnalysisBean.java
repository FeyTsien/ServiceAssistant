package com.dt.serviceassistant.bean;

import java.math.BigDecimal;
import java.util.List;

public class ComplexAnalysisBean {


   //总发货量
    private double totalshipment;
    //上月发货量
    private double monthshipment;
    private List<InventoryBean> direction_analysis;
    private List<CustomerMonthTonnage> tonnage_analysis;

    public double getTotalshipment() {
        return totalshipment;
    }

    public void setTotalshipment(double totalshipment) {
        this.totalshipment = totalshipment;
    }

    public double getMonthshipment() {
        return monthshipment;
    }

    public void setMonthshipment(double monthshipment) {
        this.monthshipment = monthshipment;
    }

    public List<InventoryBean> getDirection_analysis() {
        return direction_analysis;
    }

    public void setDirection_analysis(List<InventoryBean> direction_analysis) {
        this.direction_analysis = direction_analysis;
    }

    public List<CustomerMonthTonnage> getTonnage_analysis() {
        return tonnage_analysis;
    }

    public void setTonnage_analysis(List<CustomerMonthTonnage> tonnage_analysis) {
        this.tonnage_analysis = tonnage_analysis;
    }

    public class CustomerMonthTonnage {
        /**
         * 月份
         */
        private  String month ;
        /**
         * 吨位
         */
        private BigDecimal tonnage;

        public String getMonth() {
            return month;
        }
        public void setMonth(String month) {
            this.month = month;
        }
        public BigDecimal getTonnage() {
            return tonnage;
        }

        public void setTonnage(BigDecimal tonnage) {
            this.tonnage = tonnage;
        }
    }

    public class InventoryBean {
        private  String city ;
        private  String name ;
        private BigDecimal weight ;
        private BigDecimal  days ;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getWeight() {
            return weight;
        }

        public void setWeight(BigDecimal weight) {
            this.weight = weight;
        }

        public BigDecimal getDays() {
            return days;
        }

        public void setDays(BigDecimal days) {
            this.days = days;
        }
    }
}
