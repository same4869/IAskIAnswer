package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.HotelItem;

/**
 * @author xwang
 *
 * 2014年10月23日
 */
public class HotelResponse {
	public int code;//309000
	public String text;
	public List<HotelItem> content = new ArrayList<HotelItem>();
}
