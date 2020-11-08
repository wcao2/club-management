package com.ascending.training.club.controller;

import com.ascending.training.club.model.Image;
import com.ascending.training.club.model.User;
import com.ascending.training.club.service.FileService;
import com.ascending.training.club.service.ImageService;
import com.ascending.training.club.service.MessageService;
import com.ascending.training.club.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@RequestMapping(value={"/files"})
public class FileController {
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private MessageService messageService;

//    @Value("${aws.s3.bucketName}")
//    private String bucketName;

    @RequestMapping(value = "",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest req)  {
        logger.info("test file name: "+file.getOriginalFilename());
        HttpSession session=req.getSession();
        Long id = (Long)session.getAttribute("UserId");
        if(id==null)
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("the user Id is empty");
        User user = userService.getUserById(id);
        if(user==null)
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("the user is not exist");
        //TODO
        String s3Key= null;
        try {
            //should try catch this to send this problem to client side to handle
            s3Key = fileService.uploadFileToS3(file);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("fail to upload file");
        }
        Image image=new Image(user,file.getOriginalFilename(),s3Key, LocalDateTime.now());
        //save to postgresql
        imageService.save(image);
        //preparation send to sqs
        HashMap map=new HashMap();
        map.put("id",image.getId());
        map.put("email",user.getEmail());
        map.put("fileName",image.getFileName());
        map.put("s3key",image.getS3Key());
        map.put("time",image.getCreateTime().toString());
        JSONObject json=new JSONObject(map);
        //send to  sqs
        messageService.sendMessage(json.toString(),5);
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(s3Key);
    }

}






























