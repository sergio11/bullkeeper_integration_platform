package sanchez.sanchez.sergio.bullkeeper.fcm.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import sanchez.sanchez.sergio.bullkeeper.fcm.operations.FCMNotificationOperation;
import sanchez.sanchez.sergio.bullkeeper.fcm.response.FirebaseResponse;

public interface IPushNotificationsService {

    CompletableFuture<ResponseEntity<Map<String, String>>> createNotificationGroup(String notificationGroupName, List<String> deviceTokens);

    CompletableFuture<ResponseEntity<Map<String, String>>> addDevicesToGroup(String notificationGroupName, String notificationGroupKey, List<String> deviceTokens);

    CompletableFuture<ResponseEntity<Map<String, String>>> addDeviceToGroup(String notificationGroupName, String notificationGroupKey, String deviceToken);

    CompletableFuture<ResponseEntity<Map<String, String>>> removeDevicesFromGroup(String notificationGroupName, String notificationGroupKey, List<String> deviceTokens);

    CompletableFuture<ResponseEntity<Map<String, String>>> removeDeviceFromGroup(String notificationGroupName, String notificationGroupKey, String deviceToken);

    CompletableFuture<ResponseEntity<FirebaseResponse>> send(FCMNotificationOperation fcmNotificationOperation);

    CompletableFuture<Void> updateDeviceToken(String notificationGroupName, String notificationGroupKey, String oldDeviceToken, String newDeviceToken);
}
