package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.FoodItem;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class FoodResponse extends AbstractResponse {
	public int code;// 311000
	public String text;
	public List<FoodItem> content = new ArrayList<FoodItem>();
}
