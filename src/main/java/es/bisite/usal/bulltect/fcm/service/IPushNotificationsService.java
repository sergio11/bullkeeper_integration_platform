package es.bisite.usal.bulltect.fcm.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import es.bisite.usal.bulltect.fcm.operations.FCMNotificationOperation;
import es.bisite.usal.bulltect.fcm.response.FirebaseResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface IPushNotificationsService {

    CompletableFuture<ResponseEntity<Map<String, String>>> createNotificationGroup(String userid, List<String> deviceTokens);

    CompletableFuture<String> addDevicesToGroup(String userid, String notificationGroupKey, List<String> deviceTokens);

    CompletableFuture<String> addDeviceToGroup(String userid, String notificationGroupKey, String deviceToken);

    CompletableFuture<String> removeDevicesFromGroup(String userid, String notificationGroupKey, List<String> deviceTokens);

    CompletableFuture<String> removeDeviceFromGroup(String userid, String notificationGroupKey, String deviceToken);

    CompletableFuture<FirebaseResponse> send(FCMNotificationOperation fcmNotificationOperation);

    CompletableFuture<Void> updateDeviceToken(String userid, String notificationGroupKey, String oldDeviceToken, String newDeviceToken);
}
