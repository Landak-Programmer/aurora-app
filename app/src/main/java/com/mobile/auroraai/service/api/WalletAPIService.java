package com.mobile.auroraai.service.api;

import android.content.Context;

import com.android.volley.Request;
import com.mobile.auroraai.api.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class WalletAPIService extends BaseAPIService {

    @Override
    protected String getBaseEndpoint() {
        return "wallet";
    }

    public void submitTransaction(final Context context,
                                  final BigDecimal amount,
                                  final String operation,
                                  final String reference,
                                  final String type,
                                  final Long fromWalletId,
                                  final Long toWalletId,
                                  final ServerCallback serverCallback) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("amount", amount);
            jsonObject.put("operation", mapToCorrectOperation(operation));
            jsonObject.put("reference", reference);
            jsonObject.put("type", type.toLowerCase());
            if (operation.equals("Add") || operation.equals("Transfer")) {
                jsonObject.put("addWalletId", toWalletId);
            }
            if (operation.equals("Deduct") || operation.equals("Transfer")) {
                jsonObject.put("minusWalletId", fromWalletId);
            }
            super.trigger("operation", Request.Method.POST, context, jsonObject, serverCallback);
        } catch (JSONException e) {
            logError("Unable to submit transaction due to %s", e);
        }
    }

    public void getAllWallet(final Context context, final ServerCallback serverCallback) {
        super.trigger("cred", Request.Method.GET, context, null, serverCallback);
    }

    private String mapToCorrectOperation(final String operation) {
        switch (operation) {
            case "Add":
                return "add";
            case "Deduct":
                return "minus";
            case "Transfer":
                return "transaction";
            default:
                throw new UnsupportedOperationException("Unable to determine operation: " + operation);
        }
    }


    @Override
    public String getClassTag() {
        return "WalletAPIService";
    }
}
