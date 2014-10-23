package com.xun.iaskianswer.entity;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.be.NewsItem;

/**
 * @author xwang
 *
 * 2014年10月23日
 */
public class NewsResponse {
	public int code;// 301000
	public String text;
	public List<NewsItem> content = new ArrayList<NewsItem>();
}
