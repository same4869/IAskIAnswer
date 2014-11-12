package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.TrainItem;

/**
 * @author xwang
 * 
 *         2014年10月23日
 */
public class TrainResponse extends AbstractResponse {
	public int code;// 305000
	public String text;
	public List<TrainItem> content = new ArrayList<TrainItem>();
}
