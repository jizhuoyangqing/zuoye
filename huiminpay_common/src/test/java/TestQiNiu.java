import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import com.sun.deploy.net.URLEncoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * @author dupengfei
 * @date 2021/9/12 16:26
 * @Description:
 */
public class TestQiNiu {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //testUpload();
        TestDownload();
    }
//上传文件
    private static void testUpload() {
        //构造一个带指定Region区域对象的配置类，指定存储区域和存储空间选择的区域一致
        //这里的Configuration要用七牛的包  要填服务器的区域
        Configuration cfg = new Configuration(Region.huabei());

        //把配置告诉此上传对象
        UploadManager uploadManager = new UploadManager(cfg);

        //公钥，私钥需要从七牛官网中 点击头像-->密钥管理-->复制过来
        String accessKey = "kb3brOpT0cxO-2bTSXDgF3dKnsSpCyfJQO5Fwflm";
        String secretKey = "Fbys9UOoXXyJVOa1_LXve5RawlCrX3AVZk1L3Slw";

        //bucket的名称就是自己创建的空间名
        String bucket = "huiminpayguanshan";

        //默认不指定key的情况下，为null时，以文件内容的hash值作为文件名
        //如果自己传了文件名，就用自己传的文件名。
        String key = null;

        try {
            //这是上传几个字
            //byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");

            //我们改成上传图片
            File file = new File("D:\\360downloads\\夏日美女.jpg");
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] uploadBytes = IOUtils.toByteArray(fileInputStream);

            //认证，看是不是七牛云的账号
            Auth auth = Auth.create(accessKey, secretKey);
            //认证通过后得到某个命名空间的token（令牌）
            String upToken = auth.uploadToken(bucket);

            try {
                //上传文件,参数分别是 要上传的字节数组，文件名key，token令牌upToken
                Response response = uploadManager.put(uploadBytes, key, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                // 返回上传(文件)图片的标识,一个新的文件名
                // 不指定key文件名时二者一样，key是文件内容的hash值，
                // 指定了文件名后，key就是文件名，hash值就是文件内容的hash值。在云上保存的也是指定的文件名
                System.out.println(putRet.key);
                System.out.println(putRet.hash);


            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }

    }

//    下载文件
    private static void TestDownload() throws UnsupportedEncodingException {
        //七牛云中存储的文件名
        String fileName = "FltRDkM5jalyG6b7Uvl9vOeLO4hQ";
        //存储空间的域名
        String domainOfBucket = "http://qzb94fiyp.hb-bkt.clouddn.com";
        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

        //公钥，私钥需要从七牛官网中 点击头像-->密钥管理-->复制过来
        String accessKey = "kb3brOpT0cxO-2bTSXDgF3dKnsSpCyfJQO5Fwflm";
        String secretKey = "Fbys9UOoXXyJVOa1_LXve5RawlCrX3AVZk1L3Slw";

        //认证，看是不是七牛云的账号
        Auth auth = Auth.create(accessKey, secretKey);
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        //用私有的下载链接就会生成私有的下载链接
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);

    }
}
