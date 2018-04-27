package com.hpkj.gamesdk.bean;

import android.util.Log;

import com.google.gson.Gson;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

public class JsonResponseParser implements ResponseParser {
	Gson gson = new Gson();

	@Override
	public void checkResponse(UriRequest request) throws Throwable {
		// custom check ?
		// get headers ?
	}

	/**
	 * 转换result为resultType类型的对象
	 *
	 * @param resultType
	 *            返回值类型(可能带有泛型信息)
	 * @param resultClass
	 *            返回值类型
	 * @param result
	 *            字符串数据
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object parse(Type resultType, Class<?> resultClass, String result)
			throws Throwable {
		Log.w("mm", "返回字符串：" + result);
		return gson.fromJson(result, resultClass);
	}
}
