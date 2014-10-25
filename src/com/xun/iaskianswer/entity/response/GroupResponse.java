package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.GroupItem;

public class GroupResponse {
	public int code;//308000
	public String text;
	public List<GroupItem> content = new ArrayList<GroupItem>();
}
