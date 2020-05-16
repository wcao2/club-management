package com.ascendingdc.training.Listener;

import com.ascendingdc.training.service.sendEmail;
import org.json.JSONObject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class listenSQS  implements MessageListener {
    @Override
    public void onMessage(Message message) {
        // Cast the received message as TextMessage and print the text to screen.
        try {
            JSONObject jsonObject = new JSONObject(((TextMessage)message).getText());
            System.out.println("Received: "+((TextMessage)message).getText());
            String content=String.format("you uploaded at ",jsonObject.get("fileName"),jsonObject.get("s3Key"),jsonObject.get("time"));
            sendEmail.send((String)jsonObject.get("email"),"successfully uploaded file",content);
            System.out.println("email send successfully");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
