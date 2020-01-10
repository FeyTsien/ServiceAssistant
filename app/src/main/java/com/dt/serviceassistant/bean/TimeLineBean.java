package com.dt.serviceassistant.bean;

public class TimeLineBean {

    private String message;
    private String date;
    private OrderStatus status;

    public TimeLineBean(String message, String date, OrderStatus orderStatus) {
        setMessage(message);
        setDate(date);
        setStatus(orderStatus);
    }


    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date == null ? "" : date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public enum  OrderStatus {
        COMPLETED,
        ACTIVE,
        INACTIVE
    }
}
