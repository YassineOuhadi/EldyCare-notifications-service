package com.eldycare.notification.service.service;

import com.eldycare.notification.domain.Notification;
import com.eldycare.notification.dto.NotificationDto;

/**
 *
 * @author Yassine Ouhadi
 *
 */
public interface NotificationService {
    public void sendNotification(NotificationDto notification);
}
