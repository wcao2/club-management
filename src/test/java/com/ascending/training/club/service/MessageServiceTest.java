package com.ascending.training.club.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.ascending.training.club.init.AppBootStrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class MessageServiceTest {

    @Autowired
    private AmazonSQS amazonSQS;

    @Autowired
    private MessageService messageService;

    @Test
    public void sendMessageTest(){
        when(amazonSQS.getQueueUrl(anyString())).thenReturn(mock(GetQueueUrlResult.class));
        messageService.sendMessage("test",1);
        verify(amazonSQS,times(1)).sendMessage(any(SendMessageRequest.class));
    }
}
