package practice_project.project.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.UUID;

import static org.thymeleaf.util.StringUtils.substring;

/**
 * 阿里云 OSS 工具类
 */
@Component
public class LocalOSSUtils {

    private static final Logger log = LoggerFactory.getLogger(LocalOSSUtils.class);
    @Autowired
    private LocalOSSProperties localOSSProperties;
    @Autowired
    private StaPath staPath;
    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile image) throws IOException {
        //获取静态访问方式字符串形式
        String path = staPath.getPath();
        String stapath = path.substring(0, path.lastIndexOf("**"));
//        System.out.println("stapath = " + stapath);
        //获取静态资源包UploadFile的路径
        String staticPath = this.getClass().getClassLoader().getResource("UploadFile").getFile();
//        System.out.println("staticPath:" + staticPath);
        //获取参数
        int port = localOSSProperties.getPort();
        String address = localOSSProperties.getAddress();
        //文件名处理
        String locate = image.getOriginalFilename();
        String filename = UUID.randomUUID().toString()+locate.substring(locate.lastIndexOf("."));
        log.info("文件名称：{}",filename);


        //首次需生成目录
        File folder = new File(staticPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //上传文件到指定路径
        image.transferTo(new File(folder.getCanonicalPath()+"/"+filename));

        //文件访问路径（url放入浏览器中即可查看静态资源）
        String url =address+":"+port+stapath+filename;
        return url;
    }

}
