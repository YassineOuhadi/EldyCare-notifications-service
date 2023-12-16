package com.eldycare.notification.dto;

import lombok.Data;

import java.util.List;

/**
 *
 * @author Yassine Ouhadi
 *
 */
@Data
public class NotificationDto {

    private String elderId;
    private String alertMessage;
    private List<String> alertType;
    private String alertTime;

    private String location;

    private String relativeId;

    public  NotificationDto() { }

    public NotificationDto(String elderId, String alertMessage, List<String> alertType, String alertTime, String location) {
        this.elderId = elderId;
        this.alertMessage = alertMessage;
        this.alertType = alertType;
        this.alertTime = alertTime;
        this.location = location;
    }

    public NotificationDto(String elderId, String relativeId, String alertMessage, List<String> alertType, String alertTime, String location) {
        this.elderId = elderId;
        this.relativeId = relativeId;
        this.alertMessage = alertMessage;
        this.alertType = alertType;
        this.alertTime = alertTime;
        this.location = location;
    }

    public String getElderId() {
        return elderId;
    }

    public void setElderId(String elderId) {
        this.elderId = elderId;
    }

    public String getRelativeId() {
        return relativeId;
    }

    public void setRelativeId(String relativeId) {
        this.relativeId = relativeId;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public List<String> getAlertType() {
        return alertType;
    }

    public void setAlertType(List<String> alertType) {
        this.alertType = alertType;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}