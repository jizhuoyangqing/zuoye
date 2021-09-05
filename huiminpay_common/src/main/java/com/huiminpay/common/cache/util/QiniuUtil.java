package com.huiminpay.common.cache.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 七牛云工具类
 */
public class QiniuUtil {

    /**
     * 证件上传
     * @param accessKey
     * @param secretKey
     * @param bucket
     * @param bytes
     * @param fileName
     * @return
     */
    public static void uploadToQiniu(String accessKey,String secretKey,String bucket,byte[] bytes,String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            //根据accessKey，secretKey进行账户认证
            Auth auth = Auth.create(accessKey, secretKey);
            //查找指定的存储空间
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(bytes, fileName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                throw new RuntimeException(r.bodyString());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 获取要下载的文件连接
     * @param fileName
     * @param domainOfBucket
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String download(String fileName,String domainOfBucket) throws UnsupportedEncodingException {
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String finalUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        return finalUrl;
    }


}
