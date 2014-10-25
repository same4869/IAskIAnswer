package com.xun.iaskianswer.manager;

import com.xun.iaskianswer.entity.response.AbstractResponse;
import com.xun.iaskianswer.entity.response.AppResponse;
import com.xun.iaskianswer.entity.response.FlightResponse;
import com.xun.iaskianswer.entity.response.FoodResponse;
import com.xun.iaskianswer.entity.response.GroupResponse;
import com.xun.iaskianswer.entity.response.HotelResponse;
import com.xun.iaskianswer.entity.response.LotteryResponse;
import com.xun.iaskianswer.entity.response.MovieResponse;
import com.xun.iaskianswer.entity.response.NewsResponse;
import com.xun.iaskianswer.entity.response.NovelResponse;
import com.xun.iaskianswer.entity.response.PriceResponse;
import com.xun.iaskianswer.entity.response.TextResponse;
import com.xun.iaskianswer.entity.response.TrainResponse;
import com.xun.iaskianswer.entity.response.UrlResponse;

public class ResponseManager {
	public AbstractResponse productResponse(int type) {
		// 100000 文本类数据
		// 200000 网址类数据
		// 301000 小说
		// 302000 新闻
		// 304000 应用、软件、下载
		// 305000 列车
		// 306000 航班
		// 307000 团购
		// 308000 优惠,movie
		// 309000 酒店
		// 310000 彩票
		// 311000 价格
		// 312000 餐厅
		// 40001 key的长度错误（32位）
		// 40002 请求内容为空
		// 40003 key错误或帐号未激活
		// 40004 当天请求次数已用完
		// 40005 暂不支持该功能
		// 40006 服务器升级中
		// 40007 服务器数据格式异常
		switch (type) {
		case 100000:
			return new TextResponse();
		case 200000:
			return new UrlResponse();
		case 301000:
			return new NovelResponse();
		case 302000:
			return new NewsResponse();
		case 304000:
			return new AppResponse();
		case 305000:
			return new TrainResponse();
		case 306000:
			return new FlightResponse();
		case 307000:
			return new GroupResponse();
		case 308000:
			return new MovieResponse();
		case 309000:
			return new HotelResponse();
		case 310000:
			return new LotteryResponse();
		case 311000:
			return new PriceResponse();
		case 312000:
			return new FoodResponse();
		case 40001:
			return new TextResponse();
		case 40002:
			return new TextResponse();
		case 40003:
			return new TextResponse();
		case 40004:
			return new TextResponse();
		case 40005:
			return new TextResponse();
		case 40006:
			return new TextResponse();
		case 40007:
			return new TextResponse();
		default:
			return new TextResponse();
		}
	}
}
