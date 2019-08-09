package com.dt.serviceassistant.bean;

import java.math.BigDecimal;
import java.util.List;

public class CustomerAnalysisBean {

    private double  totalshipment ;
    private List<CustomerTonnage> customer_analysis ;
    private String updatetime ;

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public double getTotalshipment() {
        return totalshipment;
    }

    public void setTotalshipment(double totalshipment) {
        this.totalshipment = totalshipment;
    }

    public List<CustomerTonnage> getCustomer_analysis() {
        return customer_analysis;
    }

    public void setCustomer_analysis(List<CustomerTonnage> customer_analysis) {
        this.customer_analysis = customer_analysis;
    }

    public class CustomerTonnage {
        /**
         * 客户
         */
        private  String client ;
        /**
         * 吨位
         */
        private BigDecimal tonnage;

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public BigDecimal getTonnage() {
            return tonnage;
        }

        public void setTonnage(BigDecimal tonnage) {
            this.tonnage = tonnage;
        }
    }
}
