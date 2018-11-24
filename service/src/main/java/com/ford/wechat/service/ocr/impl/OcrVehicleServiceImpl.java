package com.ford.wechat.service.ocr.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ford.wechat.entity.ocr.OcrValue;
import com.ford.wechat.service.ocr.OcrVehicleService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.ImageBase64Utils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Neel on 2017/5/17.
 */
@Service
@Slf4j
public class OcrVehicleServiceImpl implements OcrVehicleService {


    @Value("${alibaba.ocr.url}")
    private String url;

    @Value("${alibaba.ocr.app.code}")
    private String appCode;

    @Override
    public OcrValue obtain(String base64Image) {
        log.info("obtain url: {}", this.url);

        OkHttpClient client = new OkHttpClient();
        String base64 = ImageBase64Utils.base64Content(base64Image);

        MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
        String content = "{\n  \"inputs\": [\n    {\n      \"image\": {\n        \"dataType\": 50,\n        \"dataValue\": \"" + base64 + "\"\n      }\n    }\n  ]\n}";

        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(this.url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "APPCODE " + this.appCode)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new BusinessException("行驶证识别失败");
            }
            String bodyString = response.body().string();
            log.info(StringUtils.isEmpty(bodyString) ? "bodyString is null" : bodyString);
            log.info("行驶证识别结果解析开始");

            JSONObject result = JSON.parseObject(bodyString);

            if (!result.containsKey("outputs")) {
                throw new BusinessException("行驶证识别数据格式错误");
            }
            JSONArray outputs = result.getJSONArray("outputs");
            if (outputs.size() < 1) {
                throw new BusinessException("行驶证识别失败");
            }
            JSONObject output = outputs.getJSONObject(0);
            if (!output.containsKey("outputValue")) {
                throw new BusinessException("行驶证识别数据格式错误");
            }
            JSONObject outputValue = output.getJSONObject("outputValue");
            if (!outputValue.containsKey("dataValue")) {
                throw new BusinessException("行驶证识别数据格式错误");
            }
            OcrValue value = new Gson().fromJson(outputValue.getString("dataValue"), OcrValue.class);

            if (!value.isSuccess()) {
                throw new BusinessException("行驶证识别失败");
            }
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("行驶证识别失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("行驶证识别失败");
        }
    }
}
