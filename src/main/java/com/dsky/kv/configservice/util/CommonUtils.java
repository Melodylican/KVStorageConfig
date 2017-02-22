package com.dsky.kv.configservice.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dsky.kv.configservice.model.StoreBean;
import com.dsky.kv.configservice.model.WebStoreBean;

public class CommonUtils {

	private final static Logger log = Logger.getLogger(CommonUtils.class);  
	/**
	 * 解析http header 中的cookie
	 * @param req
	 * @return
	 */
	public static Map<String, String> parseHeaderCookie(HttpServletRequest req){
		String cookieString = req.getHeader("cookie");
		if(cookieString == null || cookieString.isEmpty()){
			cookieString = req.getHeader("cookied");
		}
		log.info("cookie names string:"+JSON.toJSONString(req.getHeaderNames()));
		
		if(cookieString!=null && !cookieString.isEmpty()){
			String[] cookieArray = cookieString.split(";");
			Map<String, String> cookieRes = new HashMap<String,String>();
			for (int i = 0; i < cookieArray.length; i++) {
				String cookieItem = cookieArray[i].trim();
				String[] cookieItemKV = cookieItem.split("=");
				if(cookieItemKV.length==2){;
					cookieRes.put(cookieItemKV[0], cookieItemKV[1]);
				}
			}
			return cookieRes;
		}else{
			return null;
		}
		
	}
	
	public static int getNowTimeStamp(){
		return (int)(System.currentTimeMillis()/1000);
	}
	
	public static Map<String,String> parseArray(String text) {
		Map<String,String> map = new HashMap<String,String>();
		JSONArray jArray = com.alibaba.fastjson.JSON.parseArray(text);
		String ip="";
		String port="";
		String userName="";
		String password="";
		String weight="";
		for(int i=0;i<jArray.size();i++) {
			JSONObject jb = jArray.getJSONObject(i);
			ip +=jb.getString("ip")+"#";
			port +=jb.getString("port")+"#";
			userName += jb.getString("userName")+"#";
			password += jb.getString("password")+"#";
			if(jb.containsKey("weight"))
				weight += jb.getString("weight")+"#";
		}
		ip = ip.substring(0,ip.length()-1).replace("#", "<br>");
		port = port.substring(0,port.length()-1).replace("#", "<br>");
		userName = userName.substring(0,userName.length()-1).replace("#", "<br>");
		password = password.substring(0,password.length()-1).replace("#", "<br>");
		if(weight.length()>1)
			weight = weight.substring(0,weight.length()-1).replace("#", "<br>");
		
		map.put("ip", ip);
		map.put("port", port);
		map.put("userName", userName);
		map.put("password", password);
		map.put("weight", weight);
		return map;
	}
	
	public static WebStoreBean parsStoreBeanList2WebStoreBeanList(StoreBean sBean) {
		WebStoreBean wBean = new WebStoreBean();
		if (sBean == null)
			return null;
		else {
				wBean.setId(sBean.getId());
				wBean.setGameName(sBean.getGameName());
				wBean.setGameId(sBean.getGameId());
				wBean.setDbName(sBean.getDbName());
				wBean.setTbName(sBean.getTbName());
				Map<String ,String > map1 = parseArray(sBean.getMaster());
				if(map1 != null) {
					wBean.setmIp(map1.get("ip"));
					wBean.setmPort(map1.get("port"));
					wBean.setmUserName(map1.get("userName"));
					wBean.setmPassword(map1.get("password"));
				}
				Map<String ,String> map2 = parseArray(sBean.getSlave());
				if(map2 != null) {
					wBean.setsIp(map2.get("ip"));
					wBean.setsPort(map2.get("port"));
					wBean.setsUserName(map2.get("userName"));
					wBean.setsPassword(map2.get("password"));
					wBean.setsWeight(map2.get("weight"));
				}
			}
		return wBean;
	}
	
	
}
