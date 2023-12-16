package com.eldycare.relative.listener;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Yassine Ouhadi
 *
 */
@Component
public class relativeAsyncListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(relativeAsyncListener.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    public relativeAsyncListener(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void onMessage(Message message) {
        String elderId = new String(message.getBody(), StandardCharsets.UTF_8);

        // Process the elderId and retrieve the list of close relatives
        List<String> closeRelativeIds = getCloseRelativeIds(elderId);

        // Create a response message and set the correlation ID
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(message.getMessageProperties().getCorrelationId());
        Message responseMessage = new Message(convertListToJson(closeRelativeIds).getBytes(), messageProperties);

        // Send the response back to the notification service
        amqpTemplate.send(message.getMessageProperties().getReplyTo(), responseMessage);
    }

    private String convertListToJson(List<String> list) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            // Handle exception (e.g., log it)
            return "";
        }
    }

    private List<String> getCloseRelativeIds(String elderId) {
        // From service!
        return Arrays.asList("relative1", "relative2", "relative3");
    }
}