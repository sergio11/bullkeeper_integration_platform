package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;

/**
 * Terminal Repository Custom
 * @author sergiosanchezsanchez
 *
 */
public interface TerminalRepositoryCustom {

    /**
     * Update Screen Status
     * @param terminal
     * @param kid
     * @param screenStatus
     */
    void updateScreenStatus(final ObjectId terminal, final ObjectId kid, final ScreenStatusEnum screenStatus);
    
 
}
