package com.ascending.training.club.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Value("${aws.sqs.name}")
    private String queueName;

    @Autowired
    private AmazonSQS sqsClient;

    //send a message to sqs
    public void sendMessage(String messageBody, int delaySec){
        SendMessageRequest sendMessageRequest=new SendMessageRequest()
                .withQueueUrl(getQueueUrl(queueName))
                .withMessageBody(messageBody)
                .withDelaySeconds(delaySec);
        sqsClient.sendMessage(sendMessageRequest);
    }

    public String getQueueUrl(String name){
        GetQueueUrlResult getQueueUrlResult=sqsClient.getQueueUrl(name);
        return getQueueUrlResult.getQueueUrl();
    }
}
