package com.jnshu.article.service;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class OssService {

    private static final Logger log = LogManager.getLogger(OssService.class);
    //阿里云OSS信息
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";//endpoint地域节点,在OSS控制台设置,查看
    private static String bucketName = "wangquan-picture-storage";//存储空间名称
    private static String accessKeyId = "LTAI4Fqz2c4UYpBwVD7Lopa3";
    private static String accessKeySecret = "hcxjy5IG0heyTEoYKdY4vkza483QH2";


    //上传方法,以文件路径
    public boolean upload(String objectName , String pathname) throws Exception {
        // 创建OSSClient实例。
        /*Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream("D:\\my_code\\task6\\src\\main\\resources\\oss.properties"));
        properties.load(inputStream);*/
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        ossClient.putObject(bucketName, objectName, new File(pathname));
        //判断文件是否存在
        boolean found = ossClient.doesObjectExist(bucketName,objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
        if (! found){
            return false;
        }return true;
    }
    //迁移方法
    public ObjectListing aliyunToqCloud() throws Exception {
        /*Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream("D:\\my_code\\task6\\src\\main\\resources\\oss.properties"));
        properties.load(inputStream);*/
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ObjectListing objectListing=ossClient.listObjects(bucketName);
        List<OSSObjectSummary> summaries=objectListing.getObjectSummaries();
        for (OSSObjectSummary s : summaries){
            log.error("\t文件名: " + s.getKey());
            //遍历,下载所有Key
            ossClient.getObject(new GetObjectRequest(bucketName,s.getKey()),new File("D:/picture/"+s.getKey()+".jpg"));
            //上传key(腾讯云

        }
        ossClient.shutdown();
        return objectListing;

    }
    public boolean uploadFile(String objectName, MultipartFile file) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName,objectName, file.getInputStream());
        Boolean result = ossClient.doesObjectExist(bucketName,objectName);
        System.out.println(result);
        ossClient.shutdown();
        return result;

    }
    public void setReferer(String url){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        BucketReferer br1 = ossClient.getBucketReferer(bucketName);
        //取出现有的名单,避免覆盖
        List<String> refererList = br1.getRefererList();
        // 添加Referer白名单。Referer参数支持通配符星号（*）和问号（？）。
        refererList.add(url);
        //设置存储空间Referer列表。设为true表示Referer字段允许为空。
        BucketReferer br = new BucketReferer(false,refererList);
        ossClient.setBucketReferer(bucketName,br);
        //再读取并打印
        List<String> refererList1 = br1.getRefererList();
        for (String referer : refererList1){
            System.out.println("_____referer白名单列表: "+referer);
        }
        ossClient.shutdown();
    }
    public void pictureZoom(String objectName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String style = "image/resize,m_fixed,w_10,h_10";
        GetObjectRequest request = new GetObjectRequest(bucketName, objectName);
        request.setProcess(style);
        ossClient.getObject(request, new File(objectName));
        ossClient.putObject(bucketName,objectName,new File(objectName));
        ossClient.shutdown();
    }
    public static void main(String[] args) throws Exception {
        OssService ossService = new OssService();
        ossService.setReferer("*.console.aliyun.com");
        /*Properties properties = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream("D:\\my_code\\task6\\src\\main\\resources\\oss.properties"));
        properties.load(inputStream);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, properties.getProperty("accessKeyId"), properties.getProperty("accessKeySecret"));*/
        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        /*ossClient.putObject(bucketName, "ObjectName", new File("D:/picture/1.jpg"));
        // 关闭OSSClient。
        ossClient.shutdown();
        Callback callback = new Callback();
        callback.setCallbackUrl(callbackUrl);
        callback.setCallbackHost("localhost");
        callback.setCallbackBody("{\\\"mimeType\\\":${mimeType},\\\"size\\\":${size}}");
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);*/

        /*OSSdemo osSdemo = new OSSdemo();
        osSdemo.upload("1655","D:/picture/4.jpg");*/
        /*boolean found = ossClient.doesObjectExist(bucketName,"152");
        System.out.println(found);*/

        /*OSSdemo osSdemo = new OSSdemo();
        ObjectListing objectListing = osSdemo.aliyunToqCloud();*/


    }
}
