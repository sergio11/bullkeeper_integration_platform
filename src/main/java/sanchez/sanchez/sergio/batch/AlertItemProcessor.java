package sanchez.sanchez.sergio.batch;

import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.exception.NoDeviceGroupForUserException;
import sanchez.sanchez.sergio.fcm.operations.FCMNotificationOperation;
import sanchez.sanchez.sergio.persistence.entity.DeviceGroupEntity;
import sanchez.sanchez.sergio.persistence.repository.DeviceGroupRepository;

@Component
@StepScope
public class AlertItemProcessor  implements ItemProcessor<Object, FCMNotificationOperation> {
	
	private static Logger logger = LoggerFactory.getLogger(AlertItemProcessor.class);
	
	private final Map<String, String> parentNotificationKey = new HashMap<String, String>();
	private final DeviceGroupRepository deviceGroupRepository;
	
	public AlertItemProcessor(DeviceGroupRepository deviceGroupRepository) {
		super();
		this.deviceGroupRepository = deviceGroupRepository;
	}

	@Override
	public FCMNotificationOperation process(Object item) throws Exception {
		FCMNotificationOperation fcmNotificationOperation = new FCMNotificationOperation();
		Map<String, String> alertData = (Map<String, String>)item;
		String parent = alertData.get("parent");
		if(!parentNotificationKey.containsKey(parent)) {
			DeviceGroupEntity deviceGroupEntity = deviceGroupRepository.findByOwner(new ObjectId(parent));
			if(deviceGroupEntity == null) {
				throw new NoDeviceGroupForUserException();
			}
			fcmNotificationOperation.setNotificationKey(deviceGroupEntity.getNotificationKey());
		} else {
			fcmNotificationOperation.setNotificationKey(alertData.get(parent));
		}
		fcmNotificationOperation.setData(alertData);
		return fcmNotificationOperation;
	}
}
