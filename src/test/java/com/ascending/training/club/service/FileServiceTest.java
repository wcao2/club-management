package com.ascending.training.club.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ascending.training.club.init.AppBootStrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppBootStrap.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;
    @Autowired
    private AmazonS3 s3Client;

    @Test
    public void uploadFileTest1(){
        File testFile=new File("/home/weicao/Downloads/sampleFile33.txt");
        fileService.uploadFile(testFile);
    }

    @Test
    public void uploadFileTest2(){//
        AmazonS3 s3Client1=mock(AmazonS3.class);
        s3Client1.putObject("xxx","xxx","example string");
        verify(s3Client1,times(1)).putObject(anyString(),anyString(),anyString());
    }

    @Test
    public void uploadFileTest3(){
        File testFile=mock(File.class);
        when(testFile.getName()).thenReturn("sample.txt");
        fileService.uploadFile(null);
        verify(s3Client,times(0)).putObject(any(PutObjectRequest.class));
        fileService.uploadFile(testFile);
        verify(s3Client,times(1)).putObject(any(PutObjectRequest.class));

    }

    @Test
    public void uploadFileTest4() throws IOException {
        MultipartFile testFile=mock(MultipartFile.class);
        ObjectMetadata mockData = mock(ObjectMetadata.class);
        when(testFile.getInputStream()).thenReturn(mock(InputStream.class));
        when(mockData.getContentType()).thenReturn("xxx");
        when(testFile.getOriginalFilename()).thenReturn("yyy");

        fileService.uploadFile("yyy",testFile);
        verify(s3Client,times(1)).putObject(anyString(),anyString(),any(InputStream.class),any());
    }

    @Test
    public void getUrlTest() throws MalformedURLException {
        when(s3Client.getUrl(anyString(),anyString())).thenReturn(new URL("http","xxx",123,"xxx"));
        fileService.getUrl("xxx","xxx");
        verify(s3Client,times(1)).getUrl(anyString(),anyString());
    }
}
