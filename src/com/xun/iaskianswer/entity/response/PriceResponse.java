package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.PriceItem;

/**
 * @author xwang
 *
 * 2014年10月23日
 */
public class PriceResponse {
	public int code;//311000
	public String text;
	public List<PriceItem> content = new ArrayList<PriceItem>();
}
