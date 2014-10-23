package com.xun.iaskianswer.entity;

import java.util.ArrayList;
import java.util.List;
import com.xun.iaskianswer.entity.be.HotelItem;

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
