package com.test.testwebmanager.controller;

import com.test.testwebmanager.pojo.Result;
import com.test.testwebmanager.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController

public class UploadController {

    @Autowired
    private AliOSSUtils aliOSSUtils;
//    @PostMapping("/upload")
//    public Result upload(String username, Integer age, MultipartFile image) throws IOException {
//        log.info("文件上传：{} {} {}",username,age,image);
//        String filename = image.getOriginalFilename();
//        String newFilename = UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf("."));
//        image.transferTo(new File("C:\\Users\\86138\\Desktop\\新建文件夹 (4)\\"+newFilename));
//        return Result.success();
//    }
    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传，文件名：{}", image.getOriginalFilename());
        String url=aliOSSUtils.upload(image);
        log.info("文件上传完成，访问地址：{}",url);
        return Result.success(url);
    }
}
