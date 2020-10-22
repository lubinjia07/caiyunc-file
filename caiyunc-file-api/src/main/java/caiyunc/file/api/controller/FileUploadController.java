package caiyunc.file.api.controller;

import caiyunc.file.api.config.AliyunOSSConfig;
import caiyunc.file.api.vo.basic.ApiResponse;
import caiyunc.file.api.vo.response.UpFileResponseVO;
import caiyunc.file.service.FileBizService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.UUID;

@RequestMapping("/filemanager/home")
@RestController
public class FileUploadController {

    @Autowired
    private AliyunOSSConfig aliyunOSSConfig;

    @Autowired
    private FileBizService fileBizService;

    @PostMapping("/create_oss_bucket")
    public String createOssBucket() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。

        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(aliyunOSSConfig.getEndpoint(), aliyunOSSConfig.getAccessKeyId(), aliyunOSSConfig.getAccessKeySecret());


        // 创建CreateBucketRequest对象。
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(aliyunOSSConfig.getBucketName());

        // 创建存储空间。
        ossClient.createBucket(createBucketRequest);


        // 关闭OSSClient。
        ossClient.shutdown();
        return "ok";
    }


    @PostMapping("/up_file")
    public ApiResponse<UpFileResponseVO> handleFileUpload(@RequestParam("file") MultipartFile mutForm) {
        if (!mutForm.isEmpty()) {
            try {

                File file = new File(mutForm.getOriginalFilename());

                int index = file.getName().lastIndexOf(".");
                String file_suffix = file.getName().substring(index + 1).toLowerCase();

                String fileUUIDName = UUID.randomUUID().toString();
                String fileName = StringUtils.isEmpty(file_suffix) ? fileUUIDName : (fileUUIDName + "." + file_suffix);

                // 创建OSSClient实例。
                OSS ossClient = new OSSClientBuilder().build(aliyunOSSConfig.getEndpoint(), aliyunOSSConfig.getAccessKeyId(), aliyunOSSConfig.getAccessKeySecret());
                // 创建PutObjectRequest对象。
                ossClient.putObject(aliyunOSSConfig.getBucketName(), fileName, new ByteArrayInputStream(mutForm.getBytes()));

                // 设置文件的访问权限为公共读。
                ossClient.setObjectAcl(aliyunOSSConfig.getBucketName(), fileName, CannedAccessControlList.PublicRead);

                String fileUrl = getFileUrl(fileName);

                fileBizService.saveFileUrl(fileName, mutForm.getOriginalFilename(), mutForm.getContentType(), (int) mutForm.getSize(), fileUrl);


                UpFileResponseVO response = new UpFileResponseVO();
                response.setKey(fileName);
                response.setName(mutForm.getOriginalFilename());
                response.setType(mutForm.getContentType());
                response.setSize((int) mutForm.getSize());
                response.setUrl(fileUrl);

                return ApiResponse.ofSuccess(response);
            } catch (Exception e) {
                e.printStackTrace();
                return ApiResponse.ofBizError("上传失败," + e.getMessage());
            }
        } else {
            return ApiResponse.ofBizError("上传失败，因为文件是空的");
        }
    }

    //lat-spring-boot-oss.oss-cn-shanghai.aliyuncs.com/
    //lat-spring-boot-oss./oss-cn-shanghai.aliyuncs.com/7015fd00-1583-49f3-8f82-1d06df14ab8a.txt
    private String getFileUrl(String fileName) {

        String endpoint = aliyunOSSConfig.getEndpoint();
        int index = endpoint.indexOf("//");
        endpoint = endpoint.substring(index + 2, endpoint.length());

        return new StringBuilder()
                .append("https://")
                .append(aliyunOSSConfig.getBucketName())
                .append(".")
                .append(endpoint)
                .append("/")
                .append(fileName)
                .toString();
    }

}
