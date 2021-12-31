package com.example.auroraai.api;

// todo: uglyyy


public class ServiceHolder {

    private static SmsInboxService smsInboxService;

    public static void init() {
        smsInboxService = new SmsInboxService();
    }

    public static SmsInboxService getSmsInboxService() {
        return smsInboxService;
    }
}
