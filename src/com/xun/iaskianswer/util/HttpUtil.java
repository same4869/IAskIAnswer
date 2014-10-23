package com.xun.iaskianswer.util;

import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.xun.iaskianswer.config.Constants;

public class HttpUtil {

	// HttpUtil工具类，这里暂时只针对图灵的请求模式，所以把url直接写进去了
	public static String httpGetUtil(String query) {
		try {
			String INFO = URLEncoder.encode(query, "utf-8");
			String requesturl = "http://www.tuling123.com/openapi/api?key="
					+ Constants.TURING_KEY + "&info=" + INFO;
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
