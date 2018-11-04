package sanchez.sanchez.sergio.masoc.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SonDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.TerminalDTO;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Mapper
public abstract class TerminalEntityDataMapper {
	
	/**
	 * Son Repository
	 */
	@Autowired
	protected SonRepository sonRepository;
	
	

	/**
	 * Terminal Entity To Terminal DTO
	 * @param terminalEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(terminalEntity.getId().toString())", target = "identity" )
    })
    @Named("terminalEntityToTerminalDTO")
    public abstract TerminalDTO terminalEntityToTerminalDTO(final TerminalEntity terminalEntity); 
	
    /**
     * 
     * @param terminalEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "terminalEntityToTerminalDTO")
    public abstract Iterable<TerminalDTO> terminalEntityToTerminalDTO(Iterable<TerminalEntity> terminalEntities);
    
	
	/**
	 * 
	 * @param saveTerminalDTO
	 * @return
	 */
	@Mappings({
    	@Mapping(expression="java(sonRepository.findOne(new org.bson.types.ObjectId(saveTerminalDTO.getSonId())))", 
    			target = "sonEntity" )
    })
    @Named("saveTerminalDtoToTerminalEntity")
    public abstract TerminalEntity saveTerminalDtoToTerminalEntity(SaveTerminalDTO saveTerminalDTO);

	/**
	 * 
	 * @param saveSocialMediaEntities
	 * @return
	 */
	@IterableMapping(qualifiedByName = "saveTerminalDtoToTerminalEntity")
    public abstract List<TerminalEntity> saveTerminalDtoToTerminalEntity(List<SaveTerminalDTO> saveTerminalDTOs);
    
	
	/**
	 * 
	 * @param saveSocialMediaEntities
	 * @return
	 */
    @IterableMapping(qualifiedByName = "saveTerminalDtoToTerminalEntity")
    public abstract List<TerminalEntity> saveTerminalDtoToTerminalEntity(Iterable<SaveTerminalDTO> saveTerminalDTOs);
	
	
}
