package sanchez.sanchez.sergio.batch;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;

import sanchez.sanchez.sergio.fcm.operations.FCMNotificationOperation;


public class AlertItemProcesor  implements ItemProcessor<Object, FCMNotificationOperation>, InitializingBean {
	
	private static Logger logger = LoggerFactory.getLogger(AlertItemProcesor.class);
	

	// Son Id -> Notification Key
	private Map<String, String> sonNotificationKey = new HashMap<String, String>();
	

	@Override
	public FCMNotificationOperation process(Object item) throws Exception {
		FCMNotificationOperation fcmNotificationOperation = new FCMNotificationOperation();
		Map<String, String> alertData = (Map<String, String>)item;
		
		
		
		
		/*logger.debug("Level -> " + alertData.get("level"));
		logger.debug("payload -> " + alertData.get("payload"));
		logger.debug("create_at -> " + alertData.get("create_at"));
		logger.debug("son -> " + alertData.get("son"));*/
		
		fcmNotificationOperation.setDeviceGroup("prueba");
		fcmNotificationOperation.setData(alertData);
		return fcmNotificationOperation;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
}
