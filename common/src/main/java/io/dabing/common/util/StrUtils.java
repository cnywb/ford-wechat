/**
 * StringUtils.java
 * Create on 2008-4-25
 * @description:com.accor.crm.utils
 */
package io.dabing.common.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

/**@description:
 * @author huangwen
 * @version: 1.0,2017-06-30
 */
public class StrUtils {
	private static final String FIELD_REGEX = "\\{([^\\}]*)\\}";
	private static final Pattern pattern = Pattern.compile(FIELD_REGEX);
	
	/**
	 * @Description: return the length of string
	 * @param string
	 * @return
	 */
	public static int length(String string){
		if( string != null ){
			return string.length();
		}else{
			return 0;
		}
	}
	
	
	public static String parseMessage(String message, Object data){
		
		Matcher m = pattern.matcher(message);
		String objectFieldName = null;
		String value = null;
		while(m.find()){
			objectFieldName = m.group().substring(1, m.group().length() - 1);
			try{
				value = BeanUtils.getProperty(data, objectFieldName);
			}catch(Exception ex){
			}
			if (value == null){
				value = "";
			}
			message = message.replaceAll(Pattern.quote(m.group()), value);
		}
		
		return message;
	}
	
	/**@Description:
	 * @param 
	 * @return
	 */
	public static boolean isNull(Integer value) {
		return value == null || value <= 0;
	}
	
	public static String trim(String string){
		return string == null ? "" : string.trim();
	}
	
	/**@Description:
	 * @param 
	 * @return
	 */
	public static boolean isNull(String string) {
		return string == null || string.trim().length() <= 0;
	}
	public static boolean equals(String str1,String str2){
		if( ( isNull(str1) && isNull(str2) )
		 || ( !isNull(str1) && str1.equals(str2) ) ){
			return true;
		}else{
			return false;
		}
	}
	public static boolean equals(Integer int1,Integer int2){
		if( ( int1 == null && int2 == null )
		 || ( int1 != null && int1.equals(int2) ) ){
			return true;
		}else{
			return false;
		}
	}

	/**@Description:
	 * @param strings
	 * @param strings2
	 * @return
	 */
	public static boolean equals(Integer[] strings, Integer[] strings2) {
		if( ( strings == null && strings2 == null ) 
		 || ( strings == null && strings2 != null && strings2.length == 0 )
		 || ( strings2 == null && strings != null && strings.length == 0 ) ){
			return true;
		}
		if( ( strings == null && strings2 != null && strings2.length > 0 )
		 || ( strings2 == null && strings != null && strings.length > 0 ) ){
			return false;
		}
		for( int i = 0 ; i < strings.length ; i ++ ){
			if( !contains(strings[i],strings2) ){
				return false;
			}
		}
		for( int i = 0 ; i < strings2.length ; i ++ ){
			if( !contains(strings2[i],strings) ){
				return false;
			}
		}
		return true;
	}
	/**@Description:
	 * @param strings
	 * @param strings2
	 * @return
	 */
	public static boolean equals(String[] strings, String[] strings2) {
		if( ( strings == null && strings2 == null ) 
		 || ( strings == null && strings2 != null && strings2.length == 0 )
		 || ( strings2 == null && strings != null && strings.length == 0 ) ){
			return true;
		}
		for( int i = 0 ; i < strings.length ; i ++ ){
			if( !contains(strings[i],strings2) ){
				return false;
			}
		}
		for( int i = 0 ; i < strings2.length ; i ++ ){
			if( !contains(strings2[i],strings) ){
				return false;
			}
		}
		return true;
	}
	public static boolean contains(String value,String[] array){
		for( int i = 0 ; i < array.length ; i ++ ){
			if( value.equals(array[i]) ){
				return true;
			}
		}
		return false;
	}
	public static boolean contains(Integer value,Integer[] array){
		for( int i = 0 ; i < array.length ; i ++ ){
			if( value.equals(array[i]) ){
				return true;
			}
		}
		return false;
	}

	/**@Description:
	 * @param retreadTyresAmount
	 * @return
	 */
	public static String nullToSpace(String value) {
		return value == null ? "" : value;
	}
	
	public static String replaceString(String value,String str1,String str2){
		return value.replace(str1, str2);
	}

	public static String removeEnter(String value){
		return value.replace("\r\n", "").replace("\r","").replace("\n", "");
	}
	
	public static String removeDoubleQuote(String value){
		return value.replace("\"", "'");
	}
	/**@Description:
	 * @param retreadTyresAmount
	 * @return
	 */
	public static String nullToSpace(Integer value) {
		return value == null ? "" : value.toString();
	}

	public static Integer nullToZero(Integer value){
		return value == null ? 0 : value;
	}
	
	public static String nullToSpace(Date value,SimpleDateFormat simpleDateFormat) {
		return value == null ? "" : simpleDateFormat.format( value );
	}
	
	/**@Description:
	 * @return
	 */
	public static String generateHashCode() {
		Date date = new Date();
		return ""+date.getTime();
	}


	public static void main(String[] args) {

	}

	

	public static String nullToSpace(Object object) {
		if( object != null ){
			return object.toString();
		}else{
			return null;
		}
	}
	
	
	public static String like(String value) {
		if( value != null ){
			return "%" + value + "%";
		}else{
			return "%";
		}
	}
	
	public static String toErrorMessage(Exception ex){
		
	   StringBuffer errorMsg = new StringBuffer();
	   errorMsg.append(ex.toString()).append("\r");
	   StackTraceElement[] trace =ex.getStackTrace();
	   for (int i=0; i < trace.length; i++){
		   errorMsg.append(trace[i]).append("\r");
	   }	   
	   return errorMsg.toString();
	}
	

	public static boolean isNull(Double value) {
		if( value != null && value.doubleValue() > 0 ){
			return false;
		}
		return true;
	}
	
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (arr == null || arr.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}
	
	/**
	 * @Description: return the int of string
	 * @param string
	 * @return
	 */
	public static int formatStringToInteger(String string){
		if( string != null ){
			return Integer.parseInt(string);
		}else{
			return 0;
		}
	}

	public static String formatOptionForSave(Integer i){
		if(i==null || i==0){
			return "N";
		}else{
			return "Y";
		}
	}
	public static String checkUnicodeString(String value) 
    {
		if(value == null){
			return null;
		}
		char[] xmlChar = value.toCharArray();
		for (int i=0; i < xmlChar.length; ++i) {
	        if (xmlChar[i] > 0xFFFD) 
	        { 
	            xmlChar[i] =' ';
	        } 
	        else if (xmlChar[i] < 0x20 && xmlChar[i] != 't' & xmlChar[i] != 'n' & xmlChar[i] != 'r')
	        {
	            xmlChar[i] =' ' ;
	        }
        }
       return new String( xmlChar );
    }

	
}
