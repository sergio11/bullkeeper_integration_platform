package es.bisite.usal.bulltect.fcm.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import es.bisite.usal.bulltect.fcm.operations.FCMNotificationOperation;
import es.bisite.usal.bulltect.fcm.response.FirebaseResponse;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface IPushNotificationsService {

    CompletableFuture<ResponseEntity<Map<String, String>>> createNotificationGroup(String notificationGroupName, List<String> deviceTokens);

    CompletableFuture<ResponseEntity<Map<String, String>>> addDevicesToGroup(String notificationGroupName, String notificationGroupKey, List<String> deviceTokens);

    CompletableFuture<ResponseEntity<Map<String, String>>> addDeviceToGroup(String notificationGroupName, String notificationGroupKey, String deviceToken);

    CompletableFuture<ResponseEntity<Map<String, String>>> removeDevicesFromGroup(String notificationGroupName, String notificationGroupKey, List<String> deviceTokens);

    CompletableFuture<ResponseEntity<Map<String, String>>> removeDeviceFromGroup(String notificationGroupName, String notificationGroupKey, String deviceToken);

    CompletableFuture<ResponseEntity<FirebaseResponse>> send(FCMNotificationOperation fcmNotificationOperation);

    CompletableFuture<Void> updateDeviceToken(String notificationGroupName, String notificationGroupKey, String oldDeviceToken, String newDeviceToken);
}
