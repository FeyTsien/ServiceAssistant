package com.dt.serviceassistant.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FundsAnalysisBean {

    private double  amount ;
    private String updatetime ;
    private  List<Funds> funds_analysis ;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public List<Funds> getFunds_analysis() {
        return funds_analysis;
    }

    public void setFunds_analysis(List<Funds> funds_analysis) {
        this.funds_analysis = funds_analysis;
    }

    public class Funds {

        /**
         * 入账时间
         */
        private String rdate;

        /**
         * 入账
         */
        private BigDecimal inbill;

        /**
         * 出账
         */
        private BigDecimal outbill;

        /**
         * 其他入账
         */
        private BigDecimal otherinbill;

        /**
         * 其他出账
         */
        private BigDecimal otheroutbill;

        /**
         * 余额
         */
        private BigDecimal balance;


        public String getRdate() {
            return rdate;
        }

        public void setRdate(String rdate) {
            this.rdate = rdate;
        }

        /**
         * 获取入账
         *
         * @return inbill - 入账
         */
        public BigDecimal getInbill() {
            return inbill;
        }

        /**
         * 设置入账
         *
         * @param inbill 入账
         */
        public void setInbill(BigDecimal inbill) {
            this.inbill = inbill;
        }

        /**
         * 获取出账
         *
         * @return outbill - 出账
         */
        public BigDecimal getOutbill() {
            return outbill;
        }

        /**
         * 设置出账
         *
         * @param outbill 出账
         */
        public void setOutbill(BigDecimal outbill) {
            this.outbill = outbill;
        }

        /**
         * 获取其他入账
         *
         * @return otherinbill - 其他入账
         */
        public BigDecimal getOtherinbill() {
            return otherinbill;
        }

        /**
         * 设置其他入账
         *
         * @param otherinbill 其他入账
         */
        public void setOtherinbill(BigDecimal otherinbill) {
            this.otherinbill = otherinbill;
        }

        /**
         * 获取其他出账
         *
         * @return otheroutbill - 其他出账
         */
        public BigDecimal getOtheroutbill() {
            return otheroutbill;
        }

        /**
         * 设置其他出账
         *
         * @param otheroutbill 其他出账
         */
        public void setOtheroutbill(BigDecimal otheroutbill) {
            this.otheroutbill = otheroutbill;
        }

        /**
         * 获取余额
         *
         * @return balance - 余额
         */
        public BigDecimal getBalance() {
            return balance;
        }

        /**
         * 设置余额
         *
         * @param balance 余额
         */
        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }




    }
}
