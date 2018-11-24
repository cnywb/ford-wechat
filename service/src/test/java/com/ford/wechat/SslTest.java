package com.ford.wechat;

import com.google.gson.Gson;
import io.dabing.common.http.SSLUtils;
import io.dabing.common.http.TrustAllCerts;
import io.dabing.common.http.TrustAllHostnameVerifier;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 * Created by Neel on 2017/5/29.
 */
public class SslTest {


    /**
     * 测试代码
     * @param args
     * @throws Exception
     */
    public static final void main(String[] args) throws Exception {

        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(SSLUtils.createSSLSocketFactory(), new TrustAllCerts())
                .hostnameVerifier(new TrustAllHostnameVerifier()).build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\"zip\":\"1\",\"password\":\"123456\",\"user_name\":\"wumderman\",\"business_type\":\"WD02\",\"content\":[{\"batchNo\":\"2016061400001\",\"sendDate\":\"2017-05-29 15:37:32\",\"follow\":\"1\",\"verify\":\"2\",\"vin\":\"LVSFAFMB73F010011\",\"dealerScan\":\"0\",\"dateNo\":\"20160614\",\"status\":\"1\"},{\"batchNo\":\"2016061400001\",\"sendDate\":\"2017-05-29 15:37:32\",\"follow\":\"1\",\"verify\":\"2\",\"vin\":\"LVSFAFMB73F010011\",\"dealerScan\":\"0\",\"dateNo\":\"20160614\",\"status\":\"1\"},{\"batchNo\":\"2016061400001\",\"sendDate\":\"2017-05-29 15:37:32\",\"follow\":\"1\",\"verify\":\"2\",\"vin\":\"LVSFAFMB73F010011\",\"dealerScan\":\"0\",\"dateNo\":\"20160614\",\"status\":\"1\"},{\"batchNo\":\"2016061400001\",\"sendDate\":\"2017-05-29 15:37:32\",\"follow\":\"1\",\"verify\":\"2\",\"vin\":\"LVSFAFMB73F010011\",\"dealerScan\":\"0\",\"dateNo\":\"20160614\",\"status\":\"1\"}]}");

        Request request = new Request.Builder()
                .url("https://58.43.196.68/DataReciveService")
                .post(body)
                .addHeader("content-type", "application/json")
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            Gson gson = new Gson();

            System.out.println(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
