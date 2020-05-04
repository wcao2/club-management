package com.ascending.training.club.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import com.google.common.io.Files;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Service
public class FileService {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;

    public void uploadFile(File file){
        if(file!=null){
            PutObjectRequest req=new PutObjectRequest(bucketName,file.getName(),file);
            System.out.println(file.getName().toString());
            s3Client.putObject(req);
        }else {
            logger.error("cannot upload the file");
        }
    }

    public String uploadFile(String bucketName, MultipartFile file){
        String uuid= UUID.randomUUID().toString();
        String originalFileName=file.getOriginalFilename();
        String newFileName=uuid+"."+ Files.getFileExtension(originalFileName);
        ObjectMetadata objectMetadata=new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        try{
            s3Client.putObject(bucketName,newFileName,file.getInputStream(),objectMetadata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFileName;
    }

    public String getUrl(String bucketName,String s3Key){
        URL url=s3Client.getUrl(bucketName,s3Key);
        return url.toString();
    }
}
