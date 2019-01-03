package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IGeofenceService;
import sanchez.sanchez.sergio.bullkeeper.mapper.GeofenceEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GeofenceRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGeofenceDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GeofenceDTO;

/**
 * Geofence Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class GeofenceServiceImpl implements IGeofenceService {
	
	/**
	 * Geofence Repository
	 */
	private final GeofenceRepository geofenceRepository;
	
	/**
	 * Geofence Mapper
	 */
	private final GeofenceEntityMapper geofenceMapper;
	

	/**
	 * 
	 * @param geofenceRepository
	 * @param geofenceMapper
	 */
	public GeofenceServiceImpl(
			final GeofenceRepository geofenceRepository, 
			final GeofenceEntityMapper geofenceMapper) {
		super();
		this.geofenceRepository = geofenceRepository;
		this.geofenceMapper = geofenceMapper;
	}

	/**
	 * Save
	 */
	@Override
	public GeofenceDTO save(final SaveGeofenceDTO saveGeofenceDTO) {
		Assert.notNull(saveGeofenceDTO, "Save Geofence can not be null");
		
		// Map To Entity
		final GeofenceEntity geofenceEntityToSave = geofenceMapper
				.saveGeofenceDTOToGeofenceEntity(saveGeofenceDTO);
		
		// Save Geofence
		final GeofenceEntity geofenceEntitySaved = geofenceRepository
				.save(geofenceEntityToSave);
		
		// Map Result
		return geofenceMapper.geofenceEntityToGeofenceDTO(geofenceEntitySaved);
	}

	/**
	 * All By Kid
	 */
	@Override
	public Iterable<GeofenceDTO> allByKid(final ObjectId kid) {
		Assert.notNull(kid, "Kid can not be null");
	
		// Find All Geofences by kid
		final Iterable<GeofenceEntity> geofencesList = 
				geofenceRepository.findAllByKid(kid);
		// Map Result
		return geofenceMapper.geofenceEntityToGeofenceDTOs(geofencesList);
	}

	/**
	 * Delete All By Kid
	 */
	@Override
	public void deleteAllByKid(final ObjectId kid) {
		Assert.notNull(kid, "Kid can not be null");
		
		// Delete By Kid
		geofenceRepository.deleteAllByKid(kid);
		
	}

	/**
	 * Delete By Kid And Ids
	 */
	@Override
	public void deleteByKidAndIds(final ObjectId kid, final List<ObjectId> ids) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(ids, "ids can not be null");
		
		geofenceRepository.deleteAllByKidAndIdIn(kid, ids);
	}

}
