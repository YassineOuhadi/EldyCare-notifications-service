package com.eldycare.notification.service.Impl;

import com.eldycare.notification.domain.Notification;
import com.eldycare.notification.dto.NotificationDto;
import com.eldycare.notification.repository.NotificationRepository;
import com.eldycare.notification.service.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Yassine Ouhadi
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${notification.rabbitmq.relativeQueue}")
    private String relativeQueue;

    @Value("${notification.rabbitmq.notificationQueue}")
    private String notificationQueue;

    private transient final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void sendNotification(NotificationDto notificationDto) {

        String elderId = notificationDto.getElderId();

        // Verify elder person > auth service
        // .. for now, assume user was verified

        List<String> closeRelativeIds = getCloseRelativeIds(elderId);

        // Create a CountDownLatch to synchronize the main thread with the worker threads
        CountDownLatch latch = new CountDownLatch(closeRelativeIds.size());

        // Create a fixed-size thread pool
        ExecutorService executorService;
        if (!closeRelativeIds.isEmpty()) {
            executorService = Executors.newFixedThreadPool(closeRelativeIds.size());
        } else {
            // Handle the case where closeRelativeIds is empty
            executorService = Executors.newFixedThreadPool(1);
        }

        // Create a list to store the tasks
        List<Runnable> tasks = new ArrayList<>();

        // Send notification for all close relatives using multithreading
        for (String closeRelativeId : closeRelativeIds) {
            tasks.add(() -> {
                // Modify this part to include the convertAndSend function
                sendNotificationToRelative(new Notification(notificationDto.getElderId(),
                        closeRelativeId,
                        notificationDto.getAlertMessage(),
                        notificationDto.getAlertType(),
                        notificationDto.getAlertTime(),
                        notificationDto.getLocation()));

                // Notify the CountDownLatch that a thread has finished
                latch.countDown();
            });
        }

        // Submit all tasks
        for (Runnable task : tasks) {
            executorService.submit(task);
        }

        // Shutdown the executor to prevent new tasks from being submitted
        executorService.shutdown();

        try {
            // Wait for all threads to finish
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Handle interruption
        }
    }

    // Method to send notification to a specific relative
    private void sendNotificationToRelative(Notification notification) {
        // Save the notification to MongoDB
        notificationRepository.save(notification);
        amqpTemplate.convertAndSend(notificationQueue, notification);
    }

    private List<String> getCloseRelativeIds(String elderId) {
        // Send request to user service
        Object response = amqpTemplate.convertSendAndReceive(relativeQueue, elderId);

        // Process the response
        if (response instanceof List<?>) {
            // Cast the response to List<String>
            List<String> closeRelativeIds = (List<String>) response;
//            logger.info("Received response: {}", closeRelativeIds);
            return closeRelativeIds;
        } else {
            // Handle the case when the reply is not of the expected type
            logger.info("Unexpected response type: {}", response.getClass());
            return Collections.emptyList(); // Or handle it differently based on your requirements
        }
    }

}
