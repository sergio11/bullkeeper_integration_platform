package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public interface SupervisedChildrenRepositoryCustom {
	

	/**
	 * @param id
	 */
	void acceptSupervisedChildrenNoConfirm(final ObjectId id);
	
	
	/**
	 * @param id
	 */
	void acceptSupervisedChildrenNoConfirmById(final ObjectId id);

}
