package io.dabing.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Neel on 2017/5/23.
 */
public class VinUtils {

    private VinUtils() {}

    public static final Map<String, String> brands = new HashMap<>();
    public static final Map<String, String> types = new HashMap<>();
    public static final Map<String, String> models = new HashMap<>();

    public static final Map<String, String> l = new HashMap<>();

    static {
        brands.put("A", "嘉年华");
        brands.put("B", "蒙迪欧");
        brands.put("C", "福克斯");
        brands.put("D", "马自达");
        brands.put("E", "沃尔沃S40");
        brands.put("F", "福特");
        brands.put("G", "沃尔沃S80");
        brands.put("H", "长安");
        brands.put("J", "翼虎");
        brands.put("K", "翼搏");
        brands.put("L", "佳跃");
        brands.put("M", "金牛座");

        types.put("A", "5门轿车");
        types.put("B", "3门轿车");
        types.put("F", "4门轿车");
        types.put("T", "双门、单排轿车");
        types.put("S", "轻型客车");
        types.put("C", "多用途乘用车");
        types.put("D", "运动型乘用车");

        models.put("FFF", "S-MAX 2.3L");
        models.put("AFB", "嘉年华");
        models.put("FNN", "新嘉年华1.0T");
        models.put("FNL", "新嘉年华1.5L");
        models.put("KFN", "翼搏1.0T");
        models.put("KFL", "翼搏1.5L");
        models.put("CFE", "经典福克斯");
        models.put("CEB", "新福克斯1.6L");//1
        models.put("CEC", "新福克斯2.0L");
        models.put("CEN", "全新福克斯1.0T");
        models.put("CEL", "全新福克斯1.5T");
//        models.put("CEB", "全新福克斯1.6L");//1
        models.put("FSL", "福睿斯");
        models.put("JEL", "翼虎1.5T");//2
        models.put("JEB", "翼虎1.6T");
        models.put("JEC", "翼虎2.0L");//3
//        models.put("JEL", "全新翼虎1.5T");//2
//        models.put("JEC", "全新翼虎2.0L");//3
        models.put("FFF", "麦柯斯");
        models.put("BFF", "蒙迪欧-致胜2.3L");
        models.put("BFC", "蒙迪欧Gtdi");
        models.put("FFL", "新蒙迪欧1.5T");//4
        models.put("FFC", "新蒙迪欧2.0L");//5
//        models.put("FFL", "全新蒙迪欧1.5T");//4
//        models.put("FFC", "全新蒙迪欧2.0L");//5
        models.put("FHC", "锐界2.0L");
        models.put("FHH", "锐界2.7L");
        models.put("MHL", "金牛座1.5T");
        models.put("MHC", "金牛座2.0L");
        models.put("MHH", "金牛座2.7L");


        models.put("FF", "新蒙迪欧");//S-MAX
        models.put("CF", "福克斯");//
        models.put("CE", "新福克斯");//
        models.put("FS", "福睿斯");//
        models.put("AF", "嘉年华");//
        models.put("FN", "新嘉年华");//
        models.put("MH", "金牛座");//
        models.put("BF", "蒙迪欧");//
        models.put("FH", "锐界");//
        models.put("KF", "翼搏");//
        models.put("JE", "翼虎");//

        l.put("A", "1.3L");//
        l.put("B", "1.6L");//
        l.put("C", "2.0L");//
        l.put("D", "2.5L");//
        l.put("E", "1.8L");//
        l.put("F", "2.3L");//
        l.put("G", "2.6L");//
        l.put("H", "2.7L");//
        l.put("J", "2.5L");//
        l.put("K", "2.4L");//
        l.put("L", "1.5L");//
        l.put("M", "3.0L");//
        l.put("N", "1.0L");//
        l.put("P", "1.1L");//
        l.put("R", "2.0L");//
        l.put("S", "2.5L");//
    }

    public static String vin2brand(String vin) {
        if (StringUtils.isBlank(vin) || vin.length() < 5) return "";

        String key = vin.substring(4, 5);

        String value = brands.get(key);

        return value;
    }

    public static String vin2type(String vin) {
        if (StringUtils.isBlank(vin) || vin.length() < 5) return "";

        String key = vin.substring(5, 6);

        String value = types.get(key);

        return value;
    }

    public static String vin2model(String vin) {
        if (StringUtils.isBlank(vin) || vin.length() < 17) return "";
        String key5 = vin.substring(4, 5);
        String key11 = vin.substring(10, 11);
        String key8 = vin.substring(7, 8);

        String value = models.get(key5 + key11 + key8);
        if (value != null) return value;

        value = models.get(key5 + key11);
        if (value == null) return null;

        String lvalue = l.get(key8);
        if (lvalue == null) return value;

        return value + lvalue;
    }



    public static final void main(String[] args) throws Exception {
        String vin1 = VinUtils.vin2brand("LVSHFFAL0HS200652");
        String vin2 = VinUtils.vin2type("LVSHFFAL0HS200652");
        String vin3 = VinUtils.vin2model("LVSHFFAL0HS200652");
        System.out.println(vin1);
        System.out.println(vin2);
        System.out.println(vin3);
    }

}
