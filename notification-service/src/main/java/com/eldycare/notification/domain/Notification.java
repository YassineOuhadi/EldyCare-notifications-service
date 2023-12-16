package com.eldycare.notification.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Represents a notification document.
 *
 * @author Yassine Ouhadi
 */
@Document(collection = "notifications")  // Specify the MongoDB collection name
@Data
public class Notification {

    @Id
    private String id;  // Automatically generated MongoDB identifier

    @Field("elderId")
    private String elderId;

    @Field("relativeId")
    private String relativeId;

    @Field("alertMessage")
    private String alertMessage;

    @Field("alertType")
    private List<String> alertType;

    @Field("alertTime")
    private String alertTime;

    @Field("location")
    private String location;

    public Notification() { }

    public Notification(String elderId, String relativeId, String alertMessage, List<String> alertType, String alertTime, String location) {
        this.elderId = elderId;
        this.relativeId = relativeId;
        this.alertMessage = alertMessage;
        this.alertType = alertType;
        this.alertTime = alertTime;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", elderId='" + elderId + '\'' +
                ", relativeId='" + relativeId + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                ", alertType=" + alertType +
                ", alertTime='" + alertTime + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
