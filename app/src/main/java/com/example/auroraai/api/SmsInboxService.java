package com.example.auroraai.api;

public class SmsInboxService extends BaseService {

    @Override
    protected String getBaseEndpoint() {
        return "sms/inbox";
    }
}
