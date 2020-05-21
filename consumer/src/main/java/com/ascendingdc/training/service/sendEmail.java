package com.ascendingdc.training.service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.net.PasswordAuthentication;
import java.util.Date;
import java.util.Properties;


public class sendEmail {
    public static void send(String receiveEmail, String subject,String content){
        String sendEmailAddress=System.getProperty("gmail.address");
        String sendEmailPwd=System.getProperty("gmail.password");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session=Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(sendEmailAddress,sendEmailPwd);
            }
        });
        //create a new message
        Message msg=new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(sendEmailAddress));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(receiveEmail));
            //msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse("wcao2@masonlive.gmu.edu"));
            msg.setSubject(subject);
            msg.setText(content);
            msg.setSentDate(new Date());
            String htmlCode="<h3>This message comes from AWS SQS CONFIRMED</h3><br/><h3 style=\"color:Tomato;\">Successfully upload the file to S3</h3>";
            msg.setContent(htmlCode ,"text/html");
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("TheEmail"+ receiveEmail+" is sent successfully");
    }
}
