package com.qdhualing.qrcodetracker.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import static com.qdhualing.qrcodetracker.model.JPushConstant.APP_KEY;
import static com.qdhualing.qrcodetracker.model.JPushConstant.MASTER_SECRET;

/**
 * @author 马鹏昊
 * @desc
 * @date 2018/3/28
 */
public class JPushUtils {

    public static void sendNotification(String alertMsg, String alias) throws Exception {
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_alias_alert(alertMsg, alias);

        PushResult result = jpushClient.sendPush(payload);
    }

    public static PushPayload buildPushObject_all_alias_alert(String alertMsg, String alias) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(alertMsg))
                .build();
    }

}
