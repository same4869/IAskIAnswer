package com.xun.iaskianswer.config;
/**
 * @author xwang
 *
 * 2014年10月23日
 */
public class Constants {
	
	/*
	 * 请登录http://developer.baidu.com，注册成为百度开发者，创建应用并开通语音技术服务后，
	 * 将对应的ApiKey和SecretKey填写后再测试
	 */
	public static final String API_KEY = "wafOVxHFPRaK5rDGD26uVCd2";

	public static final String SECRET_KEY = "cKZySz1RIGmZObvzpsfNPn0lZqNHBh2T";

	// 图灵机器人的Key，初始日均2000次
	public static final String TURING_KEY = "46ff5e4655ddf990b583bcac864d562b";
	
	//主界面提示Tips
	public static final String[] toastTips = {"今天天气怎么样", "附近好吃的", "你快乐吗", "我要写日记", "你是谁啊"};
	
	//枚举目前支持的所有类型
	public enum Light {
	       APP, FLIGHT, FOOD, GROUP, HOTEL, LOTTERY, MOVIE, NEWS, NOVEL, PRICE, TEXT, TRAIN, URL;
	}

}
