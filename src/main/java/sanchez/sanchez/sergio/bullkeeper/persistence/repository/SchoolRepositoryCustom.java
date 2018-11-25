package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SchoolEntity;

/**
 * @author sergio
 */
public interface SchoolRepositoryCustom {
	List<SchoolEntity> getAllSchoolNames();
}
