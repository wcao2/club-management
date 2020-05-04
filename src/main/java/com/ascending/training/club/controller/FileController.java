package com.ascending.training.club.controller;

import com.ascending.training.club.model.Image;
import com.ascending.training.club.model.User;
import com.ascending.training.club.service.FileService;
import com.ascending.training.club.service.ImageService;
import com.ascending.training.club.service.MessageService;
import com.ascending.training.club.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

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
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest req){
        logger.info("test file name: "+file.getOriginalFilename());
        HttpSession session=req.getSession();
        Long id = (Long)session.getAttribute("UserId");
        User user = userService.getUserById(id);
        String s3Key=fileService.uploadFile("ascending-weicao",file);
        Image image=new Image(user,file.getOriginalFilename(),s3Key, LocalDateTime.now());
        imageService.save(image);
        //sqs
        messageService.sendMessage(image.toString(),5);
        return s3Key;
    }
}
