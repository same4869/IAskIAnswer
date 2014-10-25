package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.NovelItem;

public class NovelResponse {
	public int code;//309000
	public String text;
	public List<NovelItem> content = new ArrayList<NovelItem>();
}
