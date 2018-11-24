package io.dabing.common.http;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

/**
 * Created by Neel on 2017/5/29.
 */
public class SSLUtils {

    private SSLUtils() {}

    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,  new TrustManager[] { new TrustAllCerts() }, new SecureRandom());

            ssfFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }
}
