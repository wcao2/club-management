package com.ascending.training.club.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileService {
    private String bucketName="ascending-weicao";
    private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private AmazonS3 s3Client;

    public void uploadFile(File file){
        if(file!=null){
            PutObjectRequest req=new PutObjectRequest(bucketName,file.getName(),file);
            System.out.println(file.getName());
            s3Client.putObject(req);
        }else {
            logger.error("cannot upload the file");
        }
    }
}
