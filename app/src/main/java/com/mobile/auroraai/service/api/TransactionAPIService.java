package com.mobile.auroraai.service.api;

import android.content.Context;

import com.android.volley.Request;
import com.mobile.auroraai.api.APIHelper;
import com.mobile.auroraai.api.ServerCallback;

public class TransactionAPIService extends BaseAPIService {

    @Override
    protected String getBaseEndpoint() {
        return "transaction";
    }

    @Override
    public String getClassTag() {
        return "TransactionAPIService";
    }

    public void getTypeChoice(final Context context, final ServerCallback serverCallbackResponseArray) {
        super.trigger("types", Request.Method.GET, context, null, serverCallbackResponseArray);
    }
}
