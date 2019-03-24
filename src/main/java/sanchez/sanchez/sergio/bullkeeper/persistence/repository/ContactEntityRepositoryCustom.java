package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;

/**
 * 
 * @author ssanchez
 *
 */
public interface ContactEntityRepositoryCustom {

	/**
	 * Disable Contact
	 * @param kid
	 * @param terminal
	 * @param contact
	 */
	void disableContact(final ObjectId kid, final ObjectId terminal, final ObjectId contact);
	
}
