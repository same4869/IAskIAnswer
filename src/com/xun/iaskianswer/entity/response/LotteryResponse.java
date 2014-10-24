package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.LotteryItem;

/**
 * @author xwang
 *
 * 2014年10月23日
 */
public class LotteryResponse {
	public int code;//310000
	public String text;
	public List<LotteryItem> content = new ArrayList<LotteryItem>();
}
