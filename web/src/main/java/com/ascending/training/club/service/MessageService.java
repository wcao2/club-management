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

    //when make bean, it will use MessageService constructor
    public MessageService(@Autowired AmazonSQS amazonSQS){
        this.sqsClient=amazonSQS;
        //test rm this line, this line need to check with getAmazonSQS in AWSConfigTest
        //queUrl = getQueUrl(System.getProperty("aws.sqs.name"));
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
        //when test getQueueUrlTest:if put getQueUrl in constructor AND without ... in AWSConfigTest
        // now, constructor calls getQueUrl(),sqsClient is mock [????but injection doesn't finish:wrong]
        // sqsClient is mock=>so use getQueueUrl will return null=>getQueueUrlResult is null
        //==========================  DI==>CONSTRUSTOR==>TEST METHOD
        // when test getQueueUrlTest:if put getQueUrl in constructor AND with ... in AWSConfigTest
        // 1st:sqsClient,getQueueUrlResult all mock(execute constructor method:getQueUrl(System.getProperty("aws.sqs.name"));)
        // 2nd: in @Test getQueueUrlTest method
        //==========================
        //two way to solve:
        // 1: remove  queUrl = getQueUrl(System.getProperty("aws.sqs.name"));  in constructor
        // 2: don't remove, and add ... in AWSConfigTest
        GetQueueUrlResult getQueueUrlResult=sqsClient.getQueueUrl(name);
        return getQueueUrlResult.getQueueUrl();
    }
}
