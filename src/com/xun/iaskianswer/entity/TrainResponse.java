package com.xun.iaskianswer.entity;

import java.util.ArrayList;
import java.util.List;
import com.xun.iaskianswer.entity.be.TrainItem;

/**
 * @author xwang
 *
 * 2014年10月23日
 */
public class TrainResponse {
	public int code;//305000
	public String text;
	public List<TrainItem> content = new ArrayList<TrainItem>();
}
