package io.dabing.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;

public class XStremUtil {

	public static void main(String[] args) {
		 
	
		
	}
	
	
	public static <T> T toObject(String xmlStr, Class<T>[] cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        xstream.ignoreUnknownElements();
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }

    public static <T> T toObject(InputStream inputStream,String readerCharset, Class<T>[] cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        xstream.ignoreUnknownElements();
        @SuppressWarnings("unchecked")
        InputStreamReader fileReader=new InputStreamReader(inputStream,Charset.forName(readerCharset) );
        T t = (T) xstream.fromXML(fileReader);
        return t;
    }
	
	public static <T> T toObject(String xmlFilePath,String readerCharset, Class<T>[] cls) throws FileNotFoundException {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        xstream.ignoreUnknownElements();
        InputStreamReader fileReader=new InputStreamReader(new FileInputStream(new File(xmlFilePath)),Charset.forName(readerCharset) );
        T t = (T)xstream.fromXML(fileReader);
        return t;
    }
	
	public static <T> T toObject(String xmlFilePath,String readerCharset, Class<T>[] cls,Class supperCls,Class childCls) throws FileNotFoundException {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        xstream.addDefaultImplementation(childCls, supperCls);
        InputStreamReader fileReader=new InputStreamReader(new FileInputStream(new File(xmlFilePath)),Charset.forName(readerCharset) );
        T t = (T)xstream.fromXML(fileReader);
        return t;
    }

    public static String toXml(Object obj, Class[] cls){
        replaceCharacterEntity(obj);
        XStream xstream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xstream.processAnnotations(cls);
        return xstream.toXML(obj);
    }

    /**
     * 将属性中的所有关键字用转义字符替换掉,以防产生不规范的xml
     * @param o
     */
    public static  void replaceCharacterEntity(Object o){
        Field[] fields=o.getClass().getDeclaredFields();

        for(int i=0;i<fields.length;i++){

            if (fields[i].getType().toString().equals("class java.lang.String")){

                String value=(String)getFieldValueByName(fields[i].getName(),o);

                try {
                    fields[i].setAccessible(true);
                    fields[i].set(o,replaceCharacterEntity(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }else if(fields[i].getType().toString().equals("interface java.util.List")){
                List list= (List)getFieldValueByName(fields[i].getName(),o);
                for(Object l:list){
                    replaceCharacterEntity(l);
                }
            }
        }
    }

    public static String replaceCharacterEntity(String src){
        return src==null?null:src.replace("<","&lt;").replace(">","&gt;").replace("&","&amp;").replace("\"","&quot;").replace("'","&apos;");
    }

    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {

            return null;
        }
    }

}
