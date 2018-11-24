package com.ford.wechat.pc.controller.ftps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by lc on 2017/9/15.
 * <p>
 * ftp上传文件
 */
@Component
public class FtpService {

    private final static Logger logger = LoggerFactory.getLogger(FtpService.class);
    public String url;
    public int port;
    public String username;
    public String password;
    private FtpClient ftpClient;

    public FtpClient getFtpClient() {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(url, port);
        ftpClient = FtpClient.create();
        try {
            ftpClient.login(username, password.toCharArray());
            ftpClient.setBinaryType();
            logger.info("==========连接ftp成功============");
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    public String generateTxtFile(List datas) {
        return null;
    }

    public void upload(String localFile, String ftpFile) {
        OutputStream os = null;
        FileInputStream fileInputStream = null;
        try {
            os = ftpClient.putFileStream(ftpFile);
            File file = new File(localFile);
            byte[] bytes = new byte[4096];
            int c;
            while ((c = fileInputStream.read(bytes)) != -1) {
                os.write(bytes, 0, c);
            }
            logger.info("上传文件" + localFile + "成功");
        } catch (Exception e) {
            logger.error("文件上传失败"+localFile+"========="+e.getMessage());
        }
    }
}
