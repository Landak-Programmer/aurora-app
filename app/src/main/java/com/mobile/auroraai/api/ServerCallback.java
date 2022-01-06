package com.mobile.auroraai.api;

import com.android.volley.VolleyError;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.mobile.auroraai.core.LogAble;

public abstract class ServerCallback<T extends JsonElement> extends LogAble {

    public abstract void onSuccess(final T response);

    public void onSuccess(final JsonNull response) {

    }

    public void onError(VolleyError response) {
        if (response != null) {
            logError("Error getting response with msg : "
                    + response.getMessage() + " and code: "
                    + response.networkResponse.statusCode, response);
        }
    }
}
