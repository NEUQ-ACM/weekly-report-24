package practice_project.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import practice_project.project.pojo.Result;
import practice_project.project.utils.LocalOSSUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {

//    @PostMapping("/upload")
//    public Result upload(String username, Integer age, MultipartFile image) throws IOException {
//        log.info("文件上传：{}，{}，{}",username,age,image);
//
//        String locate = image.getOriginalFilename();
//
//        int index = locate.lastIndexOf(".");
//        String extname = locate.substring(index);
//        String newFileName = UUID.randomUUID().toString()+extname;
//        log.info("文件名：{}",newFileName);
//
//        File dir = new File("UploadFile");
//        if(!dir.exists()){
//            dir.mkdir();
//        }
//
//        image.transferTo(new File(dir.getAbsolutePath()+"/"+newFileName));
//        return Result.success();
//    }
    @Autowired
    private LocalOSSUtils localOSSUtils;
    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {


        log.info("本地存储：{}",image.getOriginalFilename());
        String url = localOSSUtils.upload(image);
        log.info("文件上传完成，url为：{}",url);

        return Result.success(url);
    }
}
