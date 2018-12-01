package sanchez.sanchez.sergio.bullkeeper.fcm.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.common.collect.Lists;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.fcm.operations.DevicesGroupOperation;
import sanchez.sanchez.sergio.bullkeeper.fcm.operations.DevicesGroupOperationType;
import sanchez.sanchez.sergio.bullkeeper.fcm.operations.FCMNotificationOperation;
import sanchez.sanchez.sergio.bullkeeper.fcm.properties.FCMCustomProperties;
import sanchez.sanchez.sergio.bullkeeper.fcm.response.FirebaseResponse;
import sanchez.sanchez.sergio.bullkeeper.fcm.service.IPushNotificationsService;
import sanchez.sanchez.sergio.bullkeeper.util.Unthrow;

import java.net.URI;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class PushNotificationsServiceImpl implements IPushNotificationsService {
	
	private static Logger logger = LoggerFactory.getLogger(PushNotificationsServiceImpl.class);
	
	private final RestTemplate restTemplate;
	private final FCMCustomProperties firebaseCustomProperties;
	
	private final static ParameterizedTypeReference<Map<String, String>> typeRef = new ParameterizedTypeReference<Map<String, String>>() {};

	@Autowired
	public PushNotificationsServiceImpl(@Qualifier("fcmRestTemplate") RestTemplate restTemplate, FCMCustomProperties firebaseCustomProperties) {
		super();
		this.restTemplate = restTemplate;
		this.firebaseCustomProperties = firebaseCustomProperties;
	}
	
	private CompletableFuture<ResponseEntity<Map<String, String>>> executeDeviceOperation(DevicesGroupOperation deviceOperation){
		return CompletableFuture.supplyAsync(() ->  Unthrow.wrap(() -> restTemplate.exchange(new URI(firebaseCustomProperties.getNotificationGroupsUrl()), HttpMethod.POST, 
				new HttpEntity<DevicesGroupOperation>(deviceOperation), typeRef)));
	}

	@Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> createNotificationGroup(String notificationGroupName, List<String> deviceTokens) {
		Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
		Assert.notEmpty(deviceTokens, "Device Tokens can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
		
		logger.debug("Notification Group Name  -> " + notificationGroupName + " device tokens -> " + deviceTokens.toString());
                
		return executeDeviceOperation(new DevicesGroupOperation(notificationGroupName, deviceTokens));
    }

    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> addDevicesToGroup(String notificationGroupName, String notificationGroupKey, List<String> deviceTokens) {
    	Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notEmpty(deviceTokens, "Device Tokens can not be empty");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug("addDevicesToGroup  -> " + notificationGroupName + " notificationGroupKey  -> " + notificationGroupKey + " device tokens -> " + deviceTokens.toString());
    
    	
    	return executeDeviceOperation(new DevicesGroupOperation(DevicesGroupOperationType.ADD, notificationGroupName, notificationGroupKey, deviceTokens));
    	
    }

    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> removeDevicesFromGroup(String notificationGroupName, String notificationGroupKey, List<String> deviceTokens) {
    	Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notEmpty(deviceTokens, "Device Tokens can not be empty");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug("removeDevicesFromGroup -> " + notificationGroupName + " notificationGroupKey -> " + notificationGroupKey + "deviceTokens -> " + deviceTokens.toString());
    	
    	return executeDeviceOperation(new DevicesGroupOperation(DevicesGroupOperationType.REMOVE, notificationGroupName, notificationGroupKey, deviceTokens));

    }

    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> addDeviceToGroup(String notificationGroupName, String notificationGroupKey, String deviceToken ) {
    	Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notNull(deviceToken, "Device Token can not be empty");
    	Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug(" addDeviceToGroup -> " + notificationGroupName + " notificationGroupKey -> " + notificationGroupKey + "deviceToken -> " + deviceToken);
    	
    	return executeDeviceOperation(new DevicesGroupOperation(DevicesGroupOperationType.ADD, notificationGroupName, notificationGroupKey, Lists.newArrayList(deviceToken)));

    }

    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> removeDeviceFromGroup(String notificationGroupName, String notificationGroupKey, String deviceToken) {
    	Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notNull(deviceToken, "Device Token can not be empty");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug(" removeDeviceFromGroup -> " + notificationGroupName + " notificationGroupKey -> " + notificationGroupKey + "deviceToken -> " + deviceToken);
    	
    	return executeDeviceOperation(new DevicesGroupOperation(DevicesGroupOperationType.REMOVE, notificationGroupName, notificationGroupKey, Lists.newArrayList(deviceToken)));
    	
    }
    
    @Override
	public CompletableFuture<ResponseEntity<FirebaseResponse>> send(FCMNotificationOperation fcmNotificationOperation) {
    	Assert.notNull(fcmNotificationOperation, "FCM Notification operation can not be null");
    	Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug(" send Firebase Notification " + fcmNotificationOperation.toString());
    	
    	return CompletableFuture.supplyAsync(() -> restTemplate.postForEntity(firebaseCustomProperties.getNotificationSendUrl(), 
    			fcmNotificationOperation, FirebaseResponse.class));
	}
    
    @Override
	public CompletableFuture<Void> updateDeviceToken(String notificationGroupName, String notificationGroupKey,
			String oldDeviceToken, String newDeviceToken) {
    	Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notNull(oldDeviceToken, "Old Device Token can not be empty");
    	Assert.notNull(newDeviceToken, "New Device Token can not be empty");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug(" updateDeviceToken  -> " + notificationGroupName + " notificationGroupKey ->  " + notificationGroupKey + " oldDeviceToken -> " + oldDeviceToken + "newDeviceToken -> " + newDeviceToken );
    	
    	
    	return CompletableFuture.allOf(
    			CompletableFuture.supplyAsync(() -> restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
    	                new DevicesGroupOperation(DevicesGroupOperationType.REMOVE, notificationGroupName, notificationGroupKey, 
    	                		Lists.newArrayList(oldDeviceToken)), String.class)),
    			CompletableFuture.supplyAsync(() ->restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
    	                new DevicesGroupOperation(DevicesGroupOperationType.ADD, notificationGroupName, notificationGroupKey, 
    	                		Lists.newArrayList(newDeviceToken)), String.class)));
    
  
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(restTemplate, "Rest Template can not be null");
		Assert.notNull(firebaseCustomProperties, "Firebase Custom Properties can not be null");
		Assert.hasLength(firebaseCustomProperties.getAppServerKey(), "App Server Key can not be empty");
		logger.debug("App Server Key -> " + firebaseCustomProperties.getAppServerKey());
	}

	
}
