package com.ascending.training.club.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

//    @Value("${aws.sqs.name}")
//    private String queueName;

    private String queUrl = null;

    //@Autowired
    private AmazonSQS sqsClient;

    public MessageService(@Autowired AmazonSQS amazonSQS){
        this.sqsClient=amazonSQS;
        //queUrl = getQueUrl(System.getProperty("aws.sqs.name"));//test rm this line
    }

    //send a message to sqs
    public void sendMessage(String messageBody, int delaySec){
        if(queUrl==null){
            queUrl = getQueUrl(System.getProperty("aws.sqs.name"));
        }

        SendMessageRequest sendMessageRequest=new SendMessageRequest()
                .withQueueUrl(queUrl)
                .withMessageBody(messageBody)
                .withDelaySeconds(delaySec);
        sqsClient.sendMessage(sendMessageRequest);
    }

    public String getQueUrl(String name){
        //test:if put getQueUrl in constructor, there will be a error=>we couldn't stub the mocked sqsClient before
        // ;sqsClient is mock, so use getQueueUrl will return null
        GetQueueUrlResult getQueueUrlResult=sqsClient.getQueueUrl(name);
        return getQueueUrlResult.getQueueUrl();
    }
}
