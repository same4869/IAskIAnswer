package com.xun.iaskianswer.manager;

import java.util.Locale;

import com.xun.iaskianswer.config.AnswerType;
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
import com.xun.iaskianswer.util.JSONUtil;

public class ResponseManager {
	//工厂类，给返回结果和类型，生成对应类型的object
	public AbstractResponse productResponse(String turingResult, AnswerType type) {
		switch (type) {
		case TEXT:
			TextResponse textResponse = JSONUtil.objectFromJSONString(
					turingResult, TextResponse.class);
			return textResponse;
		case URL:
			return new UrlResponse();
		case NOVEL:
			return new NovelResponse();
		case NEWS:
			return new NewsResponse();
		case APP:
			return new AppResponse();
		case TRAIN:
			return new TrainResponse();
		case FLIGHT:
			return new FlightResponse();
		case GROUP:
			return new GroupResponse();
		case MOVIE:
			return new MovieResponse();
		case HOTEL:
			return new HotelResponse();
		case LOTTERY:
			return new LotteryResponse();
		case PRICE:
			return new PriceResponse();
		case FOOD:
			return new FoodResponse();
		case KEY_LENGTH_ERROR:
			return new TextResponse();
		case REQUEST_NULL:
			return new TextResponse();
		case KEY_ERROR:
			return new TextResponse();
		case NO_REQUEST_COUNT:
			return new TextResponse();
		case NO_SUPPORT:
			return new TextResponse();
		case UPDATING:
			return new TextResponse();
		case FORMAT_ERROR:
			return new TextResponse();
		default:
			return new TextResponse();
		}
	}
}
