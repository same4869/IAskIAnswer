package com.xun.iaskianswer.util;

import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * @author xwang
 *
 * 2014年10月23日
 */
public class JSONUtil {
	public final static <T> String toJSONString(T t) {
		return JSON.toJSONString(t);
	}

	public final static <T> List<T> arrayFromJSONString(String jsonString,
			Class<T> clazz) {
		return JSON.parseArray(jsonString, clazz);
	}

	public final static <T> T objectFromJSONString(String jsonString,
			Class<T> clazz) {
		return JSON.parseObject(jsonString, clazz);
	}
}
