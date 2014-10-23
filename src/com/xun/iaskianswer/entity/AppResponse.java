package com.xun.iaskianswer.entity;

import java.util.ArrayList;
import java.util.List;
import com.xun.iaskianswer.entity.be.AppItem;

/**
 * @author xwang
 *
 * 2014年10月23日
 */
public class AppResponse {
	public int code;// 304000
	public String text;
	public List<AppItem> content = new ArrayList<AppItem>();
}
