package sanchez.sanchez.sergio.batch;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.fcm.operations.FCMNotificationOperation;
import sanchez.sanchez.sergio.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.service.IPushNotificationsService;

@Component
@StepScope
public class SendNotificationsWriter implements ItemWriter<FCMNotificationOperation> {
	
	private static Logger logger = LoggerFactory.getLogger(SendNotificationsWriter.class);
	
	private final IPushNotificationsService pushNotificationsService;
	private final AlertRepository alertRepository;

	public SendNotificationsWriter(IPushNotificationsService pushNotificationsService, AlertRepository alertRepository) {
		super();
		this.pushNotificationsService = pushNotificationsService;
		this.alertRepository = alertRepository;
	}

	@Override
	public void write(List<? extends FCMNotificationOperation> items) throws Exception {
		logger.debug("Notifications To Send -> " + items.toString());

		List<ObjectId> ids = items.stream().filter(fcmNotification -> fcmNotification.getData().containsKey("id") 
				&& fcmNotification.getData().get("id") != null)
					.map(fcmNotification -> new ObjectId(fcmNotification.getData().get("id")))
					.collect(Collectors.toList());
		
		logger.debug("Total Ids -> " + ids.size());
		alertRepository.setAsDelivered(ids);
	}

}
