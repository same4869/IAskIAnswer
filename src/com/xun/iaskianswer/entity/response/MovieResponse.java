package com.xun.iaskianswer.entity.response;

import java.util.ArrayList;
import java.util.List;

import com.xun.iaskianswer.entity.item.MovieItem;

public class MovieResponse extends AbstractResponse {
	public int code;// 308000
	public String text;
	public List<MovieItem> content = new ArrayList<MovieItem>();
}
