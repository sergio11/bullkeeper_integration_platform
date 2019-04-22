package sanchez.sanchez.sergio.bullkeeper.batch;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ReaderNotOpenException;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.bullkeeper.batch.models.AlertsByGuardian;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertDeliveryModeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;

/**
 * Alert Item Reader
 * @author sergiosanchezsanchez
 *
 */
@Component("alertItemReader")
@StepScope
public class AlertItemReader extends AbstractItemCountingItemStreamItemReader<AlertsByGuardian> {

	private Logger logger = LoggerFactory.getLogger(AlertItemReader.class);
	
	private final GuardianRepository guardianRepository;
	private final AlertRepository alertRepository;
	
	private List<GuardianEntity> guardians;
	
	/**
	 * 
	 * @param guardianRepository
	 * @param alertRepository
	 */
	public AlertItemReader(GuardianRepository guardianRepository, AlertRepository alertRepository) {
		super();
		this.guardianRepository = guardianRepository;
		this.alertRepository = alertRepository;
		this.setName("alertItemReader");
		this.setExecutionContextName("alertItemReader");
	}
	
	/**
	 * 
	 */
	@Override
	protected void doOpen() throws Exception {
		logger.debug("AlertItemReader doOpen called ...");
		guardians = guardianRepository
				.findByPreferencesPushNotificationsEnabledAndActiveTrueAndLockedFalseAndPendingDeletionFalse(Boolean.TRUE);
		setMaxItemCount(guardians.size()); 
		
	}
	
	/**
	 * 
	 */
	@Override
	protected AlertsByGuardian doRead() throws Exception {
		if (guardians == null) {
            throw new ReaderNotOpenException("Reader must be open before it can be read.");
        }
		logger.debug("AlertItemReader doRead called ...");
		GuardianEntity currentParent = guardians.get(getCurrentItemCount() - 1);
		List<AlertEntity> alerts = alertRepository
				.findByGuardianIdAndDeliveredFalseAndDeliveryModeAndCreateAtGreaterThan(currentParent.getId(),
						AlertDeliveryModeEnum.PUSH_NOTIFICATION, currentParent.getLastAccessToAlerts());
		return new AlertsByGuardian(currentParent.getId(), alerts);
	}


	/**
	 * 
	 */
	@Override
	protected void doClose() throws Exception {
		logger.debug("AlertItemReader doClose called ...");
		if(guardians != null)
			guardians.clear();
	    setMaxItemCount(0);
	    setCurrentItemCount(0);
	}
	
}
