package com.mobile.auroraai.service;

import com.mobile.auroraai.service.api.SmsInboxAPIService;
import com.mobile.auroraai.service.api.TransactionAPIService;
import com.mobile.auroraai.service.api.WalletAPIService;
import com.mobile.auroraai.service.internal.WhatsappService;

public class ServiceHolder {

    private static boolean isInit = false;

    private static SmsInboxAPIService smsInboxService;
    private static WalletAPIService walletAPIService;
    private static TransactionAPIService transactionAPIService;

    private static WhatsappService whatsappService;

    public static void init() {
        if (!isInit) {
            smsInboxService = new SmsInboxAPIService();
            walletAPIService = new WalletAPIService();
            transactionAPIService = new TransactionAPIService();

            whatsappService = new WhatsappService();
        }
        isInit = true;
    }

    public static SmsInboxAPIService getSmsInboxService() {
        return smsInboxService;
    }

    public static WalletAPIService getWalletAPIService() {
        return walletAPIService;
    }

    public static TransactionAPIService getTransactionAPIService() {
        return transactionAPIService;
    }

    public static WhatsappService getWhatsappService() {
        return whatsappService;
    }
}
