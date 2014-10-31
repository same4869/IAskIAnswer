package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.FlightItem;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class FlightResponse extends AbstractResponse {
	public int code;// 306000
	public String text;
	public List<FlightItem> list = new ArrayList<FlightItem>();
}
