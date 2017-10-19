package es.bisite.usal.bulltect.fcm.service.impl;

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

import es.bisite.usal.bulltect.fcm.operations.DevicesGroupOperation;
import es.bisite.usal.bulltect.fcm.operations.DevicesGroupOperationType;
import es.bisite.usal.bulltect.fcm.operations.FCMNotificationOperation;
import es.bisite.usal.bulltect.fcm.properties.FCMCustomProperties;
import es.bisite.usal.bulltect.fcm.response.FirebaseResponse;
import es.bisite.usal.bulltect.fcm.service.IPushNotificationsService;
import es.bisite.usal.bulltect.util.Unthrow;
import io.jsonwebtoken.lang.Assert;
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

	@Autowired
	public PushNotificationsServiceImpl(@Qualifier("FCMRestTemplate") RestTemplate restTemplate, FCMCustomProperties firebaseCustomProperties) {
		super();
		this.restTemplate = restTemplate;
		this.firebaseCustomProperties = firebaseCustomProperties;
	}

	@Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> createNotificationGroup(String userid, List<String> deviceTokens) {
		Assert.notNull(userid, "User id can not be null");
		Assert.notEmpty(deviceTokens, "Device Tokens can not be null");
		Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
		
		String notificationGroupName = String.format("%s_%s", firebaseCustomProperties.getGroupPrefix(), userid);
		
		logger.debug("createNotificationGroup with user_id -> " + userid + " device tokens -> " + deviceTokens.toString());
		logger.debug("Notification Group Name -> " + notificationGroupName);
                
                ParameterizedTypeReference<Map<String, String>> typeRef = new ParameterizedTypeReference<Map<String, String>>() {};
		
        return CompletableFuture.supplyAsync(() ->  Unthrow.wrap(() -> restTemplate.exchange(new URI(firebaseCustomProperties.getNotificationGroupsUrl()), HttpMethod.POST, 
				new HttpEntity<DevicesGroupOperation>(new DevicesGroupOperation(notificationGroupName, deviceTokens)), typeRef)));
    }

    @Async
    @Override
    public CompletableFuture<String> addDevicesToGroup(String userid, String notificationGroupKey, List<String> deviceTokens) {
    	Assert.notNull(userid, "User id can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notEmpty(deviceTokens, "Device Tokens can not be empty");
    	Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug("addDevicesToGroup with user_id -> " + userid + " notificationGroupKey  -> " + notificationGroupKey + " device tokens -> " + deviceTokens.toString());
    	
    	String notificationGroupName = String.format("%s_%s", firebaseCustomProperties.getGroupPrefix(), userid);
        return CompletableFuture.supplyAsync(() -> restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                new DevicesGroupOperation(DevicesGroupOperationType.ADD, notificationGroupName, notificationGroupKey, deviceTokens), String.class));
    }

    @Async
    @Override
    public CompletableFuture<String> removeDevicesFromGroup(String userid, String notificationGroupKey, List<String> deviceTokens) {
    	Assert.notNull(userid, "User id can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notEmpty(deviceTokens, "Device Tokens can not be empty");
    	Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug("removeDevicesFromGroup User id -> " + userid + " notificationGroupKey -> " + notificationGroupKey + "deviceTokens -> " + deviceTokens.toString());
    	
    	String notificationGroupName = String.format("%s_%s", firebaseCustomProperties.getGroupPrefix(), userid);
        return CompletableFuture.supplyAsync(() ->restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                new DevicesGroupOperation(DevicesGroupOperationType.REMOVE, notificationGroupName, notificationGroupKey, deviceTokens), String.class));
    }

    @Async
    @Override
    public CompletableFuture<String> addDeviceToGroup(String userid, String notificationGroupKey, String deviceToken ) {
    	Assert.notNull(userid, "User id can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notNull(deviceToken, "Device Token can not be empty");
    	Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug(" addDeviceToGroup User id -> " + userid + " notificationGroupKey -> " + notificationGroupKey + "deviceToken -> " + deviceToken);
    	
    	String notificationGroupName = String.format("%s_%s", firebaseCustomProperties.getGroupPrefix(), userid);
        return CompletableFuture.supplyAsync(() ->restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                new DevicesGroupOperation(DevicesGroupOperationType.ADD, notificationGroupName, notificationGroupKey, 
                		Lists.newArrayList(deviceToken)), String.class));
    }

    @Async
    @Override
    public CompletableFuture<String> removeDeviceFromGroup(String userid, String notificationGroupKey, String deviceToken) {
    	Assert.notNull(userid, "User id can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notNull(deviceToken, "Device Token can not be empty");
    	Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug(" removeDeviceFromGroup User id -> " + userid + " notificationGroupKey -> " + notificationGroupKey + "deviceToken -> " + deviceToken);
    	
    	String notificationGroupName = String.format("%s_%s", firebaseCustomProperties.getGroupPrefix(), userid);
        return CompletableFuture.supplyAsync(() -> restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                new DevicesGroupOperation(DevicesGroupOperationType.REMOVE, notificationGroupName, notificationGroupKey, 
                		Lists.newArrayList(deviceToken)), String.class));
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
	public CompletableFuture<Void> updateDeviceToken(String userid, String notificationGroupKey,
			String oldDeviceToken, String newDeviceToken) {
    	Assert.notNull(userid, "User id can not be null");
    	Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
    	Assert.notNull(oldDeviceToken, "Old Device Token can not be empty");
    	Assert.notNull(newDeviceToken, "New Device Token can not be empty");
    	Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
    	
    	logger.debug(" updateDeviceToken user id -> " + userid + " notificationGroupKey ->  " + notificationGroupKey + " oldDeviceToken -> " + oldDeviceToken + "newDeviceToken -> " + newDeviceToken );
    	
    	String notificationGroupName = String.format("%s_%s", firebaseCustomProperties.getGroupPrefix(), userid);
    	
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
