package sanchez.sanchez.sergio.masoc.domain.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.masoc.domain.service.ITerminalService;
import sanchez.sanchez.sergio.masoc.mapper.TerminalEntityDataMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.ITerminalRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.TerminalDTO;

/**
 * Terminal Service
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class TerminalServiceImpl implements ITerminalService {
	
	private Logger logger = LoggerFactory.getLogger(TerminalServiceImpl.class);

	/**
	 * Terminal Entity Data Mapper
	 */
	private final TerminalEntityDataMapper terminalEntityDataMapper;
	
	/**
	 * Terminal Repository
	 */
	private final ITerminalRepository terminalRepository;

	/**
	 * 
	 * @param terminalEntityDataMapper
	 * @param terminalRepository
	 */
	@Autowired
	public TerminalServiceImpl(final TerminalEntityDataMapper terminalEntityDataMapper, final ITerminalRepository terminalRepository) {
		super();
		this.terminalEntityDataMapper = terminalEntityDataMapper;
		this.terminalRepository = terminalRepository;
	}

	/**
	 * Save
	 */
	@Override
	public TerminalDTO save(final SaveTerminalDTO saveTerminalDTO) {
		Assert.notNull(saveTerminalDTO, "Save Terminal can not be null");
		
		logger.debug("Save Terminal");
		// Map DTO to Entity
		final TerminalEntity terminalEntityToSave = 
				terminalEntityDataMapper.saveTerminalDtoToTerminalEntity(saveTerminalDTO);
        // Save Terminal Entity
		final TerminalEntity terminalEntitySaved = 
				terminalRepository.save(terminalEntityToSave);
	
        logger.debug("Terminal Entity Saved -> " + terminalEntitySaved.toString());
        
        return terminalEntityDataMapper.terminalEntityToTerminalDTO(terminalEntitySaved);
		
	}

	/**
	 * Get Terminales By Child ID
	 */
	@Override
	public Iterable<TerminalDTO> getTerminalsByChildId(final String childId) {
		Assert.notNull(childId, "Child Id can not be null");
		Assert.hasLength(childId, "Child id can not be empty");
		
		final List<TerminalEntity> terminalEntities = 
				terminalRepository.findBySonEntityId(new ObjectId(childId));
		
		return terminalEntityDataMapper
				.terminalEntityToTerminalDTO(terminalEntities);
		
	}

	/**
	 * Delete By Id
	 */
	@Override
	public void deleteById(ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		terminalRepository.delete(id);
	}

}
