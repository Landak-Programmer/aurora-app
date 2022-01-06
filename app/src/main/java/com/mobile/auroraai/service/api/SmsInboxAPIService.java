package com.mobile.auroraai.service.api;

public class SmsInboxAPIService extends BaseAPIService {

    @Override
    protected String getBaseEndpoint() {
        return "sms/inbox";
    }

    @Override
    public String getClassTag() {
        return "BaseAPIService";
    }
}
