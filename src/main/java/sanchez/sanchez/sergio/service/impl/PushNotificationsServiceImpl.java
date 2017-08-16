package sanchez.sanchez.sergio.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.common.collect.Lists;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.fcm.FCMCustomProperties;
import sanchez.sanchez.sergio.fcm.exception.FCMOperationException;
import sanchez.sanchez.sergio.fcm.operations.DevicesGroupOperation;
import sanchez.sanchez.sergio.fcm.operations.DevicesGroupOperationType;
import sanchez.sanchez.sergio.service.IPushNotificationsService;

@Service
public class PushNotificationsServiceImpl implements IPushNotificationsService {
	
	private static Logger logger = LoggerFactory.getLogger(PushNotificationsServiceImpl.class);
	
	private final RestTemplate restTemplate;
	private final FCMCustomProperties firebaseCustomProperties;


	public PushNotificationsServiceImpl(RestTemplate restTemplate, FCMCustomProperties firebaseCustomProperties) {
		super();
		this.restTemplate = restTemplate;
		this.firebaseCustomProperties = firebaseCustomProperties;
	}

	@Async
    @Override
    public CompletableFuture<String> createNotificationGroup(String notificationGroupName, List<String> deviceTokens) {
        return CompletableFuture.supplyAsync(() -> restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(), 
				new DevicesGroupOperation(notificationGroupName, deviceTokens), String.class));
    }

    @Async
    @Override
    public CompletableFuture<String> createNotificationGroup(String notificationGroupName) {
    	Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
    	Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");
        return CompletableFuture.supplyAsync(() -> restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(), 
				new DevicesGroupOperation(notificationGroupName), String.class));
    }

    @Async
    @Override
    public CompletableFuture<String> addDevicesToGroup(String notificationGroupName, String notificationGroupKey, List<String> deviceTokens) {
        return CompletableFuture.completedFuture(restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                new DevicesGroupOperation(DevicesGroupOperationType.ADD, notificationGroupName, notificationGroupKey, deviceTokens), String.class));
    }

    @Async
    @Override
    public CompletableFuture<String> removeDevicesFromGroup(String notificationGroupName, String notificationGroupKey, List<String> deviceTokens) {
        return CompletableFuture.completedFuture(restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                new DevicesGroupOperation(DevicesGroupOperationType.REMOVE, notificationGroupName, notificationGroupKey, deviceTokens), String.class));
    }

    @Async
    @Override
    public CompletableFuture<String> addDeviceToGroup(String notificationGroupName, String notificationGroupKey, String deviceToken ) {
        return CompletableFuture.completedFuture(restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                new DevicesGroupOperation(DevicesGroupOperationType.ADD, notificationGroupName, notificationGroupKey, 
                		Lists.newArrayList(deviceToken)), String.class));
    }

    @Async
    @Override
    public CompletableFuture<String> removeDeviceFromGroup(String notificationGroupName, String notificationGroupKey, String deviceToken) {
        return CompletableFuture.completedFuture(restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                new DevicesGroupOperation(DevicesGroupOperationType.REMOVE, notificationGroupName, notificationGroupKey, 
                		Lists.newArrayList(deviceToken)), String.class));
    }
	
	@PostConstruct
	protected void init(){
		Assert.notNull(restTemplate, "Rest Template can not be null");
		Assert.notNull(firebaseCustomProperties, "Firebase Custom Properties can not be null");
		Assert.hasLength(firebaseCustomProperties.getAppServerKey(), "App Server Key can not be empty");
		logger.debug("App Server Key -> " + firebaseCustomProperties.getAppServerKey());
	}

}
