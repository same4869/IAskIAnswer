package com.xun.iaskianswer.util;

import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.xun.iaskianswer.config.Constants;
import com.xun.iaskianswer.entity.bean.LocationInfo;

public class HttpUtil {
	private static LocationInfo locationInfo;

	// HttpUtil工具类，这里暂时只针对图灵的请求模式，所以把url直接写进去了
	public static String httpGetUtil(String query) {
		locationInfo = LocationInfo.getInstance();
		String requesturl = null;
		try {
			String INFO = URLEncoder.encode(query, "utf-8");
			// 如果在请求的时候已经拿到了地址就把地址扔到请求参数里面去，因为有些query需要地址信息
			if (locationInfo.getAddrStr() == null) {
				requesturl = "http://www.tuling123.com/openapi/api?key="
						+ Constants.TURING_KEY + "&info=" + INFO;
			} else {
				requesturl = "http://www.tuling123.com/openapi/api?key="
						+ Constants.TURING_KEY + "&info=" + INFO + "&loc="
						+ locationInfo.getAddrStr();
			}
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(requesturl);
			HttpResponse response = client.execute(request);

			// 200即正确的返回码
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
