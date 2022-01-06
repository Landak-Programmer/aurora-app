package com.mobile.auroraai.api;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CustomJsonRequest extends JsonRequest<JsonElement> {

    private final Response.Listener<JsonElement> mResponseListener;

    public CustomJsonRequest(int method, String url, JSONObject requestObject, Response.Listener<JsonElement> responseListener, Response.ErrorListener errorListener) {
        super(method, url, (requestObject == null) ? null : requestObject.toString(), responseListener, errorListener);
        mResponseListener = responseListener;
    }

    @Override
    protected void deliverResponse(JsonElement response) {
        mResponseListener.onResponse(response);
    }

    @Override
    protected Response<JsonElement> parseNetworkResponse(final NetworkResponse response) {
        try {
            final String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            final JsonElement obj = JsonParser.parseString(json);
            return Response.success(obj,
                    HttpHeaderParser.parseCacheHeaders(response));
            // needed?
                /*if (response.data.length == 0)
                    return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
                else
                    return Response.success(obj,
                            HttpHeaderParser.parseCacheHeaders(response))*/
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
