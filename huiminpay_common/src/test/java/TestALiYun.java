import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.*;

/**
 * @author dupengfei
 * @date 2021/9/12 20:40
 * @Description:
 */
public class TestALiYun {
    public static void main(String[] args) throws IOException {
//        testUpload();
        testDownload();
    }

    private static void testUpload() throws IOException {

//        这是上传byte数组的形式
//        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
//        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
//
//        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//        String accessKeyId = "LTAI5tJsELFwHj8gdvpPyRKv";
//        String accessKeySecret = "1GVkmq2HT33xXL2EUIxlFeIBISQubt";
//
//
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
//
//        // 填写Byte数组。
//        //byte[] content = "Hello OSS".getBytes();
//
//        //我们改成上传图片
//        File file = new File("D:\\360downloads\\黑客.jpg");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        byte[] content = IOUtils.readStreamAsByteArray(fileInputStream);
//        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
//        ossClient.putObject("aliguanshan", "图片/自己起个名字.jpg", new ByteArrayInputStream(content));
//
//        // 关闭OSSClient。
//        ossClient.shutdown();


        //这是上传文件流的形式
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tJsELFwHj8gdvpPyRKv";
        String accessKeySecret = "1GVkmq2HT33xXL2EUIxlFeIBISQubt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        // 依次填写Bucket名称（例如examplebucket）、Object完整路径（例如exampledir/exampleobject.txt）和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。                            也可以直接写个文件名，不要目录
        PutObjectRequest putObjectRequest = new PutObjectRequest("aliguanshan", "自己起个目录名/自己给文件起个名字黑客.jpg", new File("D:\\360downloads\\黑客.jpg"));

//      如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//      ObjectMetadata metadata = new ObjectMetadata();
//      metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
//      metadata.setObjectAcl(CannedAccessControlList.Private);
//      putObjectRequest.setMetadata(metadata);

//      上传文件。
        ossClient.putObject(putObjectRequest);

//      关闭OSSClient。
        ossClient.shutdown();
    }

//下载文件
    private static void testDownload() {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tJsELFwHj8gdvpPyRKv";
        String accessKeySecret = "1GVkmq2HT33xXL2EUIxlFeIBISQubt";
        // 填写Bucket名称。
        String bucketName = "aliguanshan";
        // 填写不包含Bucket名称在内的Object完整路径，例如testfolder/exampleobject.txt。
        String objectName = "自己起个目录名/自己给文件起个名字黑客.jpg";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载Object到本地文件，并保存到指定的本地路径中。如果指定的本地文件存在会覆盖，不存在则新建。
        // 如果未指定本地路径，则下载后的文件默认保存到示例程序所属项目对应本地路径中。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("D:\\360downloads\\对从阿里云下载的黑客再起个名字.jpg"));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
