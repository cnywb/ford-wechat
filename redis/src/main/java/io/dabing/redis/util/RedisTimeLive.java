package io.dabing.redis.util;

/**
 * Redis Key 有效时间定义
 * Created by wanglijun on 16/7/30.
 */
public class RedisTimeLive {

    /**有效为一天*/
    public static final int DAY_ONE=60*60*24;

    /**有效为两天*/
    public static final int DAY_TWO=60*60*24*2;
    /**有效为一周*/
    public static final int WEEK_ONE=60*60*24*7;

    /**有效为一小时*/
    public static final int HOUR_1=60*60;
    /**半小时,30分钟*/
    public static final int  HALFHOUR=60*30;
    /**有效为两小时*/
    public static final int HOUR_2=60*60*2;
    /**有效为两小时*/
    public static final int HOUR_10=60*60*10;
    /***一分钟*/
    public static final int MINUTE_1=60;
    /***两分钟*/
    public static final int MINUTE_2=60*2;
    /***20分钟*/
    public static final int MINUTE_20=60*20;
    /***30分钟*/
    public static final int MINUTE_30=60*30;
    /***15分钟*/
    public static final int QUARTER=60*30;
    /***
     * 10秒
     */
    public static final int SECOND_10=10;
    /***
     * 15秒
     */
    public static final int SECOND_15=15;
    /***
     * 30秒
     */
    public static final int SECOND_30=30;

}
