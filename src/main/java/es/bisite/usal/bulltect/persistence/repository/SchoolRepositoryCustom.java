package es.bisite.usal.bulltect.persistence.repository;

import java.util.List;

import es.bisite.usal.bulltect.persistence.entity.SchoolEntity;

/**
 * @author sergio
 */
public interface SchoolRepositoryCustom {
	List<SchoolEntity> getAllSchoolNames();
}
