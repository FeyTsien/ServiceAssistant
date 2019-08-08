package com.dt.serviceassistant.bean;

import java.util.List;

public class ShipAnalysisBean {

    private List<InfoBean> ship_set_info;
    private List<InfoBean> configuration_info;
    private List<InfoBean> shipping_info;

    public List<InfoBean> getShip_set_info() {
        return ship_set_info;
    }

    public void setShip_set_info(List<InfoBean> ship_set_info) {
        this.ship_set_info = ship_set_info;
    }

    public List<InfoBean> getConfiguration_info() {
        return configuration_info;
    }

    public void setConfiguration_info(List<InfoBean> configuration_info) {
        this.configuration_info = configuration_info;
    }

    public List<InfoBean> getShipping_info() {
        return shipping_info;
    }

    public void setShipping_info(List<InfoBean> shipping_info) {
        this.shipping_info = shipping_info;
    }

    public static class InfoBean {
        /**
         * name : 中兴16
         * location : 北京码头
         * sign_date : 2019-05-01
         * shipping_date : ysquvucbpc
         * weight : 4839
         * configuration_tonnage : 520
         */


        private String name;
        private String agencyname;
        private String location;
        private String sign_date;
        private String arrival_date;
        private String weight;
        private String unit_price;
        private String shipping_date;
        private String stowage_date;
        private String configuration_tonnage;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSign_date() {
            return sign_date;
        }

        public void setSign_date(String sign_date) {
            this.sign_date = sign_date;
        }

        public String getShipping_date() {
            return shipping_date;
        }

        public void setShipping_date(String shipping_date) {
            this.shipping_date = shipping_date;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getConfiguration_tonnage() {
            return configuration_tonnage;
        }

        public void setConfiguration_tonnage(String configuration_tonnage) {
            this.configuration_tonnage = configuration_tonnage;
        }

        public String getAgencyname() {
            return agencyname;
        }

        public void setAgencyname(String agencyname) {
            this.agencyname = agencyname;
        }

        public String getArrival_date() {
            return arrival_date;
        }

        public void setArrival_date(String arrival_date) {
            this.arrival_date = arrival_date;
        }

        public String getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
        }

        public String getStowage_date() {
            return stowage_date;
        }

        public void setStowage_date(String stowage_date) {
            this.stowage_date = stowage_date;
        }
    }
}
