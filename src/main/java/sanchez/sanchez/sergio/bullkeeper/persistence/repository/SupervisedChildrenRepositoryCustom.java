package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public interface SupervisedChildrenRepositoryCustom {
	

	/**
	 * @param guardian
	 */
	void acceptSupervisedChildrenNoConfirm(final ObjectId guardian);
	
	
	/**
	 * @param id
	 */
	void acceptSupervisedChildrenNoConfirmById(final ObjectId id);

}
