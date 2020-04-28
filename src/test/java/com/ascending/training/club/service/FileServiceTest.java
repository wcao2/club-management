package com.ascending.training.club.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ascending.training.club.init.AppBootStrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.*;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;
    @Autowired
    private AmazonS3 s3Client;

    @Test
    public void uploadFileTest(){
        /*//1
        File testFile=new File("/home/weicao/Downloads/sampleFile3.txt");
        fileService.uploadFile(testFile);*/

        //2
        /*AmazonS3 s3Client=mock(AmazonS3.class);
        s3Client.putObject("xxx","xxx","String of object");
        verify(s3Client,times(1)).putObject(anyString(),anyString(),anyString());*/

        //3:final and real test
        File testFile=mock(File.class);

//        fileService.uploadFile(null);
//        verify(s3Client,times(0)).putObject(any(PutObjectRequest.class));
//        when(testFile.getName()).thenReturn("example.txt");

        fileService.uploadFile(testFile);
        verify(s3Client,times(1)).putObject(any(PutObjectRequest.class));
    }
}
