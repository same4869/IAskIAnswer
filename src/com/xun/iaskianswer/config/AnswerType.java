package com.xun.iaskianswer.config;

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
public enum AnswerType {
	TEXT(100000), URL(200000), NOVEL(301000), NEWS(302000), APP(304000), TRAIN(
			305000), FLIGHT(306000), GROUP(307000), MOVIE(308000), HOTEL(309000), LOTTERY(
			310000), PRICE(311000), FOOD(312000), KEY_LENGTH_ERROR(40001), REQUEST_NULL(
			40002), KEY_ERROR(40003), NO_REQUEST_COUNT(40004), NO_SUPPORT(40005), UPDATING(
			40006), FORMAT_ERROR(40007);
	private int nCode;

	AnswerType(int nCode) {
		this.nCode = nCode;
	}
}