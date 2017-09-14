package es.bisite.usal.bullytect.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import es.bisite.usal.bullytect.fcm.operations.FCMNotificationOperation;
import es.bisite.usal.bullytect.fcm.response.FirebaseResponse;

public interface IPushNotificationsService {
	CompletableFuture<String> createNotificationGroup(String userid, List<String> deviceTokens);
    CompletableFuture<String> createNotificationGroup(String userid);
    CompletableFuture<String> addDevicesToGroup(String userid, String notificationGroupKey, List<String> deviceTokens);
    CompletableFuture<String> addDeviceToGroup(String userid, String notificationGroupKey, String deviceToken);
    CompletableFuture<String> removeDevicesFromGroup(String userid, String notificationGroupKey, List<String> deviceTokens);
    CompletableFuture<String> removeDeviceFromGroup(String userid, String notificationGroupKey, String deviceToken);
    CompletableFuture<FirebaseResponse> send(FCMNotificationOperation fcmNotificationOperation);
    CompletableFuture<Void> updateDeviceToken(String userid, String notificationGroupKey, String oldDeviceToken, String newDeviceToken);
}
