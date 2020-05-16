package com.ascendingdc.training.init;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQS;
import com.ascendingdc.training.Listener.listenSQS;
import com.ascendingdc.training.config.AWSConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

@SpringBootApplication(scanBasePackages = "com.ascendingdc.training")
public class ConsumerBoot {
    public static void main(String[] args) throws JMSException {
        SpringApplication.run(ConsumerBoot.class,args);
        AWSConfig awsConfig=new AWSConfig();

        SQSConnectionFactory sqsConnectionFactory = awsConfig.getSQSConnectionFactory();

        SQSConnection connection=sqsConnectionFactory.createConnection();

        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumer=session.createConsumer(session.createQueue(System.getProperty("aws.sqs.name")));

        consumer.setMessageListener(new listenSQS());

        connection.start();
    }
}
