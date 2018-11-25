package sanchez.sanchez.sergio.bullkeeper.domain.service;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.EmailTypeEnum;

/**
 * Delete Pending Email Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface IDeletePendingEmailService {
	
	/**
	 * Delete By Send To And Type
	 * @param sendTo
	 * @param type
	 */
	void deleteBySendToAndType(final String sendTo, final EmailTypeEnum type);
}
