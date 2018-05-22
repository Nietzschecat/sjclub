package com.huitu.sjclub.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * 处理controller中获取页面参数的类
 * @author y-ke
 *
 */
public class RequestParamManager {
	
	Logger logger = Logger.getLogger("RequestParamManager");
	
	/**
	 * 
	 * @param request
	 * @param span  true ，如果是空字符串，则返回{}
	 * @return
	 * @throws Exception
	 */
	public String getRequestString(HttpServletRequest request, boolean span) throws Exception
	{
		request.setCharacterEncoding("UTF-8");

		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"UTF-8"));
		String line = null;  
		StringBuilder sb = new StringBuilder();  
		while((line = br.readLine())!=null)
		{  
		    sb.append(line);  
		} 
		String requestString = sb.toString();
		
//		if(sb.toString().trim().equals(""))  //导出excel，只能用url的get方式
//		{
//			requestString = request.getQueryString();
//			requestString = java.net.URLDecoder.decode(requestString);
//		}
		
		
		
		if(requestString == null || requestString.equals(""))
		{
			requestString = "{}";
		}
		logger.debug("接收到到的参数：" + requestString);	
		
		return requestString;
	}
	public String getRequestString(HttpServletRequest request) throws Exception
	{
		return this.getRequestString(request, false);
	}
	

	
	

	
	/**
	 * 将接收到的字符串转成对象
	 * @param requestString
	 * @param cls
	 * @return
	 */
	public Object toModelFromData(String requestString ,Class cls,String jsonElement)
	{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Object retObj;
		JSONObject json = JSONObject.fromObject(requestString);  //
		if(json.getJSONObject(jsonElement).isNullObject())
		{
			json.put(jsonElement, JSONObject.fromObject("{}"));
		}
		retObj =  gson.fromJson(json.getString(jsonElement),cls);

		return retObj;
	}
	
	public Object toModelFromData(String requestString ,Class cls)
	{
		return this.toModelFromData(requestString, cls, "data");
	}
	
	/**
	 * 将接收到的字符串转成对象
	 * @param requestString
	 * @param cls
	 * @return
	 */
	public Object toModelListFromData(String requestString ,Class cls,String jsonElement)
	{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Object retObj;

		//JSONArray json = JSONArray.fromObject(requestString);
		JSONObject json = JSONObject.fromObject(requestString);  //
		if(!json.getJSONArray(jsonElement).isArray())
		{
			json.put(jsonElement, JSONObject.fromObject("[]"));
		}
		Type objectType = type(List.class, cls);
		retObj =  gson.fromJson(json.getString(jsonElement),objectType);

		return retObj;
	}
	
	ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
	
}
