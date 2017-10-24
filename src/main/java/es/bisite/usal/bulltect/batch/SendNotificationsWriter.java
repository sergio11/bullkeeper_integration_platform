package es.bisite.usal.bulltect.batch;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.batch.models.FCMNotificationWrapper;
import es.bisite.usal.bulltect.fcm.operations.FCMNotificationOperation;
import es.bisite.usal.bulltect.fcm.service.IPushNotificationsService;
import es.bisite.usal.bulltect.persistence.repository.AlertRepository;

@Component
@StepScope
public class SendNotificationsWriter implements ItemWriter<FCMNotificationWrapper> {
	
	private static Logger logger = LoggerFactory.getLogger(SendNotificationsWriter.class);
	
	private final IPushNotificationsService pushNotificationsService;
	private final AlertRepository alertRepository;

	public SendNotificationsWriter(IPushNotificationsService pushNotificationsService, AlertRepository alertRepository) {
		super();
		this.pushNotificationsService = pushNotificationsService;
		this.alertRepository = alertRepository;
	}

	@Override
	public void write(List<? extends FCMNotificationWrapper> items) throws Exception {
		
		logger.debug("Notifications To Send -> " + items.toString());
		
		List<ObjectId> idsDelivered = items.stream().parallel()
			.map((FCMNotificationWrapper notificationWrapper) -> 
				pushNotificationsService.send(notificationWrapper.getFcmNotificationOperation())
					.thenApply(r -> notificationWrapper.getAlertIds()).join())
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
	
		logger.debug("Ids Delivered -> " + idsDelivered.size());
		alertRepository.setAsDelivered(idsDelivered);
		
	}
	

	

}
