package com.userchecking.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Map;

public class MultipartRequest extends Request {
	//String KEY_PICTURE = "Post_Image";
	private static final String TAG = "MutlipartRequest";

	private HttpEntity mHttpEntity;
	private final Class mClass;
	private Response.Listener mListener;
	private Map<String, String> mHeaders;
	private Map<String, String> params;

	public MultipartRequest(String mainUrl, HttpEntity httpEntity, Class clazz, Map<String, String> headers, Response.Listener listener, Response.ErrorListener errorListener) {
		super(Method.POST, mainUrl, errorListener);
		mHeaders = headers;
		mClass = clazz;
		mListener = listener;
		mHttpEntity = httpEntity;
	}

//	private HttpEntity buildMultipartEntity(String path) {
//		File file = new File(path);
//		return buildMultipartEntity(file);
//	}
//
//	private HttpEntity buildMultipartEntity(File file) {
//		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//		String fileName = file.getName();
//		builder.addBinaryBody(KEY_PICTURE, file, ContentType.create("image/jpg"), fileName);
//
//		return builder.build();
//	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Log.d("TAG", "GET PARAM");
		return mHeaders != null ? mHeaders : super.getHeaders();
	}

	@Override
	public String getBodyContentType() {
		return mHttpEntity.getContentType().getValue();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			mHttpEntity.writeTo(bos);
		} catch (IOException e) {
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response parseNetworkResponse(NetworkResponse response) {
		String json;
		try {
			json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			//Log.e("json", "-->" + json);

		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, String.format("Encoding problem parsing API response. NetworkResponse:%s", response.toString()), e);
			return Response.error(new ParseError(e));
		}
		try {
			return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
		} catch (Exception e) {
			Log.e(TAG, String.format("Couldn't API parse JSON response. NetworkResponse:%s", response.toString()), e);
			Log.e(TAG, String.format("Couldn't API parse JSON response. Json dump: %s", json));
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(Object arg0) {
		mListener.onResponse(arg0);
	}

}