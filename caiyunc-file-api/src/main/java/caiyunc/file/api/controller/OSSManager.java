package caiyunc.file.api.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.UUID;

public class OSSManager {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public OSSManager(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    public String upFile(byte[] bytes) throws IOException {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String yourObjectName = UUID.randomUUID().toString();

        ossClient.putObject(bucketName, yourObjectName, new ByteArrayInputStream(bytes));

        // 设置文件的访问权限为公共读。
        ossClient.setObjectAcl(bucketName, yourObjectName + ".txt", CannedAccessControlList.PublicRead);

        // 关闭OSSClient。
        ossClient.shutdown();
        return "ok";
    }

    public String upFile(byte[] bytes, String fileName, CannedAccessControlList controlList) throws IOException {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));

        // 设置文件的访问权限为公共读。
        ossClient.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);

        // 关闭OSSClient。
        ossClient.shutdown();
        return fileName;
    }


    public String upFile(File file) throws IOException {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String filePath = file.getPath();

        // 上传文件流。
        InputStream inputStream = new FileInputStream(file);

        int index = filePath.lastIndexOf(".");
        String file_suffix = filePath.substring(index + 1).toLowerCase();
        String yourObjectName = UUID.randomUUID().toString();
        String fileName = StringUtils.isEmpty(file_suffix) ? yourObjectName : (yourObjectName + "." + file_suffix);

        ossClient.putObject(bucketName, fileName + "." + file_suffix, inputStream);

        // 设置文件的访问权限为公共读。
        ossClient.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);

        // 关闭OSSClient。
        ossClient.shutdown();

        return fileName;
    }

}
