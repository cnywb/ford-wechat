package com.ford.wechat.test;

import com.alibaba.fastjson.JSON;
import io.dabing.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PacketTest {

    //最小红包额度
    private static final int MINMONEY = 20;
    //最大红包额度
    private static final int MAXMONEY = 80;
    //每个红包最大是平均值的倍数
    private static final double TIMES = 2;

    /**
     * @param money
     * @param count
     * @return
     * @Author:Neel
     * @Description: 拆分红包
     */
    public List<Integer> splitRedPackets(int money, int count) {
        if (!isRight(money, count)) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        //红包最大金额为平均金额的TIMES倍
        int max = (int) (money * TIMES / count);
        max = max > MAXMONEY ? MAXMONEY : max;
        System.out.println("max: " + max);
        for (int i = 0; i < count; i++) {
            int one = random(money, MINMONEY, max, count - i);
            list.add(one);
            money -= one;
        }
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
    private int random(int money, int minS, int maxS, int count) {
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
        if (isRight(money1, count -1)) {
            return one;
        } else {
            double avg = (double)money1 / (count - 1);

            if (avg < MINMONEY) {
                //递归调用，修改红包最大金额
                System.out.println("--------------------------");
                return random(money, min, one, count);
            } else if (avg > MAXMONEY) {
                System.out.println("avg :" + avg);
                //递归调用，修改红包最小金额
                System.out.println("++++++++++++++++++++++++++");
                return random(money, one, max, count);
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
    private boolean isRight(int money, int count) {
        double avg = (double)money / count;
        if (avg < MINMONEY) {
            return false;
        }
        if (avg > MAXMONEY) {
            return false;
        }
        return true;
    }





    public static void main(String[] args) {
        // TODO Auto-generated method stub
        List<Integer> ex = new ArrayList<>();
        for(int j = 1; j <= 1000; j++) {
            PacketTest util = new PacketTest();
            int money = 150000;
            int count = 3000;
            List<Integer> list = util.splitRedPackets(money, count);
            Collections.shuffle(list);//打乱顺序
            System.out.println(JSON.toJSON(list));

            System.out.println("红包生成总数: [" + list.size() + "]");

            if (list.size() != count) throw new BusinessException("红包数量异常 应为: " + count + "实际: " + list.size());

            int total = 0;
            int i = 0;
            for(Integer value : list) {
                i++;
                if (value > MAXMONEY || value < MINMONEY) {
                    ex.add(value);
                    if(i != list.size()) {
                        throw new BusinessException("红包金额范围异常 序号: [" + i + "] 应为: [" + MINMONEY + "~" + MAXMONEY + "]  实际: [" + value + "]");
                    }
                    System.out.println("金额超过范围, 实际金额为: [" + value + "]");
                }
                total += value.intValue();
            }
            if (total != money) {
                throw new BusinessException("总金额异常 应为: [" + money + "]  实际: [" + total + "]");
            }

            System.out.println("第" + j + "次红包生成成功!");
        }
        System.out.println("连续1000次红包生成测试全部成功!");


        System.out.println("异常金额次数: [" + ex.size() + "]  数据如下:");
        System.out.println(JSON.toJSON(ex));

//        test3();
    }
}
