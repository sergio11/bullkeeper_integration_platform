package es.bisite.usal.bulltect.batch;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import es.bisite.usal.bulltect.batch.models.AlertsByParent;
import es.bisite.usal.bulltect.batch.models.FCMNotificationWrapper;
import es.bisite.usal.bulltect.exception.NoDeviceGroupForUserException;
import es.bisite.usal.bulltect.fcm.operations.FCMNotificationOperation;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.DeviceGroupEntity;
import es.bisite.usal.bulltect.persistence.repository.DeviceGroupRepository;

@Component
@StepScope
public class AlertItemProcessor  implements ItemProcessor<AlertsByParent, FCMNotificationWrapper> {
	
	private static Logger logger = LoggerFactory.getLogger(AlertItemProcessor.class);
	
	private final DeviceGroupRepository deviceGroupRepository;
    private final IMessageSourceResolverService messageSourceResolver;
	
	public AlertItemProcessor(DeviceGroupRepository deviceGroupRepository, IMessageSourceResolverService messageSourceResolver) {
		super();
		this.deviceGroupRepository = deviceGroupRepository;
		this.messageSourceResolver = messageSourceResolver;
	}

	@Override
	public FCMNotificationWrapper process(AlertsByParent alerts) throws Exception {
		Assert.notNull(alerts, "Alerts can not be null");
		Assert.notNull(alerts.getParentId(), "Parent Id can not be null");
		Assert.notNull(alerts.getAlerts(), "Alerts can not be null");
		Assert.notEmpty(alerts.getAlerts(), "Alerts can not be empty");
		
		
		logger.debug("Count Alerts "+ alerts.getAlerts().size() +  " for user ->  " + alerts.getParentId());
		
		
		DeviceGroupEntity deviceGroupEntity = deviceGroupRepository.findByOwnerId(alerts.getParentId());
		if(deviceGroupEntity == null) {
			logger.debug("No device Group founded");
			throw new NoDeviceGroupForUserException();
		}
		
		FCMNotificationOperation fcmNotificationOperation = new FCMNotificationOperation();
		fcmNotificationOperation.setNotificationKey(deviceGroupEntity.getNotificationKey());
		
		String notificationTitle = messageSourceResolver.resolver("fcm.notifications.title", new Object[] { alerts.getAlerts().size() });
		String notificationBody;
		
		List<AlertEntity> dangerAlerts = alerts.getAlerts()
				.stream().filter((alert) -> alert.getLevel().equals(AlertLevelEnum.DANGER))
				.collect(Collectors.toList());
		
		if(!dangerAlerts.isEmpty()) {
	
			notificationBody = dangerAlerts.size() == 1 ? 
					messageSourceResolver.resolver("fcm.notifications.body.danger.one", new Object[]{ dangerAlerts.get(0).getSon().getFullName() }) :
						messageSourceResolver.resolver("fcm.notifications.body.danger.many");
	
		} else {
			
			List<AlertEntity> warningAlerts = alerts.getAlerts()
					.stream().filter((alert) -> alert.getLevel().equals(AlertLevelEnum.WARNING))
					.collect(Collectors.toList());
			
			if(!warningAlerts.isEmpty()) { 
				
				notificationBody = warningAlerts.size() == 1 ? 
						messageSourceResolver.resolver("fcm.notifications.body.warnig.one", new Object[]{ warningAlerts.get(0).getSon().getFullName() }) :
							messageSourceResolver.resolver("fcm.notifications.body.warnig.many");
				
			} else {
				notificationBody = messageSourceResolver.resolver("fcm.notifications.body.basic",  new Object[]{ 
						String.join(",", alerts.getAlerts().stream().map(alert -> alert.getSon().getFullName()).collect(Collectors.toList()))
				});
			}
		}
		
				
		fcmNotificationOperation.setNotification(new FCMNotificationOperation.FCMNotification(notificationTitle, notificationBody));
		return new FCMNotificationWrapper(fcmNotificationOperation, alerts.getAlertIds());
	}
}
