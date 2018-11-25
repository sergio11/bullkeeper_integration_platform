package sanchez.sanchez.sergio.bullkeeper.batch;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.batch.models.AlertsByGuardian;
import sanchez.sanchez.sergio.bullkeeper.batch.models.FCMNotificationWrapper;
import sanchez.sanchez.sergio.bullkeeper.exception.NoDeviceGroupForUserException;
import sanchez.sanchez.sergio.bullkeeper.fcm.operations.FCMNotificationOperation;
import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DeviceGroupEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.DeviceGroupRepository;

/**
 * Alert Item Processor
 * @author sergiosanchezsanchez
 *
 */
@Component
@StepScope
public class AlertItemProcessor  implements ItemProcessor<AlertsByGuardian, FCMNotificationWrapper> {
	
	private static Logger logger = LoggerFactory.getLogger(AlertItemProcessor.class);
	
	private final DeviceGroupRepository deviceGroupRepository;
    private final IMessageSourceResolverService messageSourceResolver;
	
	public AlertItemProcessor(DeviceGroupRepository deviceGroupRepository, IMessageSourceResolverService messageSourceResolver) {
		super();
		this.deviceGroupRepository = deviceGroupRepository;
		this.messageSourceResolver = messageSourceResolver;
	}

	@Override
	public FCMNotificationWrapper process(AlertsByGuardian alerts) throws Exception {
		Assert.notNull(alerts, "Alerts can not be null");
		Assert.notNull(alerts.getGuardian(), "Guardian Id can not be null");
		Assert.notNull(alerts.getAlerts(), "Alerts can not be null");
		Assert.notEmpty(alerts.getAlerts(), "Alerts can not be empty");
		
		
		logger.debug("Count Alerts "+ alerts.getAlerts().size() +  " for user ->  " + alerts.getGuardian());
		
		
		DeviceGroupEntity deviceGroupEntity = deviceGroupRepository.findByOwnerId(alerts.getGuardian());
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
					messageSourceResolver.resolver("fcm.notifications.body.danger.one", new Object[]{ dangerAlerts.get(0).getKid().getFullName() }) :
						messageSourceResolver.resolver("fcm.notifications.body.danger.many");
	
		} else {
			
			List<AlertEntity> warningAlerts = alerts.getAlerts()
					.stream().filter((alert) -> alert.getLevel().equals(AlertLevelEnum.WARNING))
					.collect(Collectors.toList());
			
			if(!warningAlerts.isEmpty()) { 
				
				notificationBody = warningAlerts.size() == 1 ? 
						messageSourceResolver.resolver("fcm.notifications.body.warnig.one", new Object[]{ warningAlerts.get(0).getKid().getFullName() }) :
							messageSourceResolver.resolver("fcm.notifications.body.warnig.many");
				
			} else {
				notificationBody = messageSourceResolver.resolver("fcm.notifications.body.basic",  new Object[]{ 
						String.join(",", alerts.getAlerts().stream().map(alert -> alert.getKid().getFullName()).collect(Collectors.toList()))
				});
			}
		}
		
				
		fcmNotificationOperation.setNotification(new FCMNotificationOperation.FCMNotification(notificationTitle, notificationBody));
		return new FCMNotificationWrapper(fcmNotificationOperation, alerts.getAlertIds());
	}
}
