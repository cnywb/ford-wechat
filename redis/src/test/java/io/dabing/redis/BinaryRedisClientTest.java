package io.dabing.redis;

import io.dabing.redis.client.BinaryRedisClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wanglijun on 16/7/30.
 */
public class BinaryRedisClientTest  extends  RedisAllTest {

    @Autowired
    private  BinaryRedisClient binaryRedisClient;

    @Test
    public void setUser(){

        User user=new User();
        user.setName("even");
        user.setOld(26);

        binaryRedisClient.set("user:point",user);
    }



    @Test
    public void getUser(){

        User user=binaryRedisClient.get("user:point",User.class);
        logger.info(user.toString());




    }


}
