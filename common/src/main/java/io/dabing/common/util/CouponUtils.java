package io.dabing.common.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Neel
 */
public class CouponUtils {

    /*
    //最小红包额度
    private int MINMONEY;
    //最大红包额度
    private int MAXMONEY;
    //每个红包最大是平均值的倍数
    private double TIMES;
    */

    private CouponUtils() {}


    /**
     * @param money
     * @param count
     * @return
     * @Author:Neel
     * @Description: 拆分红包
     */
    public static List<Integer> split(int money, int count, int minMoney, int maxMoney, double times) {
        if (!isRight(money, count, minMoney, maxMoney, times)) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        //红包最大金额为平均金额的TIMES倍
        int max = (int) (money * times / count);
        max = max > maxMoney ? maxMoney : max;
//        System.out.println("max: " + max);
        for (int i = 0; i < count; i++) {
            int one = random(money, minMoney, max, count - i, minMoney, maxMoney, times);
            list.add(one);
            money -= one;
        }
        Collections.shuffle(list);//打乱顺序
        return list;
    }

    /**
     * @param money
     * @param minS
     * @param maxS
     * @param count
     * @return
     * @Author:lulei
     * @Description: 随机红包额度
     */
    private static int random(int money, int minS, int maxS, int count, int minMoney, int maxMoney, double times) {
//        System.out.println(String.format("money: %d, minS: %d, maxS: %d, count: %d", money, minS, maxS, count));
        //红包数量为1，直接返回金额
        if (count == 1) {
            return money;
        }
        //如果最大金额和最小金额相等，直接返回金额
        if (minS == maxS) {
            return minS;
        }

        int m = money - count * minS + minS;

        int max = m < maxS ? m : (maxS > money ? money : maxS);
//        int max = maxS > money ? money : maxS;

//        int a = count * max - money

        int n = money - count * maxS + maxS;

        int min = n > minS ? n : minS;

        int one = min + (int)(Math.random() * (max - min + 1));;

        int money1 = money - one;

        //判断该种分配方案是否正确
        if (isRight(money1, count -1, minMoney, maxMoney, times)) {
            return one;
        } else {
            double avg = (double)money1 / (count - 1);

            if (avg < minMoney) {
                //递归调用，修改红包最大金额
                return random(money, min, one, count, minMoney, maxMoney, times);
            } else if (avg > maxMoney) {
                //递归调用，修改红包最小金额
                return random(money, one, max, count, minMoney, maxMoney, times);
            }
        }
        return one;
    }

    /**
     * @param money
     * @param count
     * @return
     * @Author:lulei
     * @Description: 此种红包是否合法
     */
    private static boolean isRight(int money, int count, int minMoney, int maxMoney, double times) {
        double avg = (double)money / count;
        if (avg < minMoney) {
            return false;
        }
        if (avg > maxMoney) {
            return false;
        }
        return true;
    }

}
