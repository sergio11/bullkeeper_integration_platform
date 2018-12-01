package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sanchez.sanchez.sergio.bullkeeper.domain.service.ISchoolService;
import sanchez.sanchez.sergio.bullkeeper.mapper.ISchoolEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SchoolEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SchoolRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddSchoolDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SchoolNameDTO;

@Service
public class SchoolServiceImpl implements ISchoolService {
	
	private final ISchoolEntityMapper schoolEntityMapper;
	private final SchoolRepository schoolRepository;
	

	/**
	 * 
	 * @param schoolEntityMapper
	 * @param schoolRepository
	 */
	public SchoolServiceImpl(ISchoolEntityMapper schoolEntityMapper, SchoolRepository schoolRepository) {
		super();
		this.schoolEntityMapper = schoolEntityMapper;
		this.schoolRepository = schoolRepository;
	}

	/**
	 * 
	 */
	@Override
	public Page<SchoolDTO> findPaginated(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
        Page<SchoolEntity> schoolPage = schoolRepository.findAll(pageable);
        return schoolPage.map(new Converter<SchoolEntity, SchoolDTO>(){
            @Override
            public SchoolDTO convert(SchoolEntity school) {
            	return schoolEntityMapper.schoolEntityToSchoolDTO(school);
            }
        });
	}

	/**
	 * 
	 */
	@Override
	public Page<SchoolDTO> findPaginated(Pageable pageable) {
		Page<SchoolEntity> schoolPage = schoolRepository.findAll(pageable);
        return schoolPage.map(new Converter<SchoolEntity, SchoolDTO>(){
            @Override
            public SchoolDTO convert(SchoolEntity school) {
               return schoolEntityMapper.schoolEntityToSchoolDTO(school);
            }
        });
	}

	/**
	 * 
	 */
	@Override
	public Page<SchoolDTO> findByNamePaginated(String name, Pageable pageable) {
		Page<SchoolEntity> schoolPage = schoolRepository.findAllByNameLikeIgnoreCase(name, pageable);
		return schoolPage.map(new Converter<SchoolEntity, SchoolDTO>(){
            @Override
            public SchoolDTO convert(SchoolEntity school) {
               return schoolEntityMapper.schoolEntityToSchoolDTO(school);
            }
        });
	}
	
	/**
	 * 
	 */
	@Override
	public Iterable<SchoolDTO> findByName(String name) {
		List<SchoolEntity> schoolsEntities = schoolRepository.findAllByNameLikeIgnoreCase(name);
		return schoolEntityMapper.schoolEntitiesToSchoolDTOs(schoolsEntities);
	}

	/**
	 * 
	 */
	@Override
	public SchoolDTO getSchoolById(String id) {
		SchoolEntity schoolEntity = schoolRepository.findOne(new ObjectId(id));
		return schoolEntityMapper.schoolEntityToSchoolDTO(schoolEntity);
	}

	/**
	 * 
	 */
	@Override
	public SchoolDTO save(AddSchoolDTO addSchoolDTO) {
		SchoolEntity schoolToSave = schoolEntityMapper.addSchoolDTOToSchoolEntity(addSchoolDTO);
		SchoolEntity schoolSaved = schoolRepository.save(schoolToSave);
		return schoolEntityMapper.schoolEntityToSchoolDTO(schoolSaved);
	}

	/**
	 * 
	 */
	@Override
	public SchoolDTO delete(String id) {
		SchoolEntity schoolToDelete = schoolRepository.findOne(new ObjectId(id));
		schoolRepository.delete(schoolToDelete);
		return schoolEntityMapper.schoolEntityToSchoolDTO(schoolToDelete);
	}

	@Override
	public Iterable<SchoolNameDTO> getAllSchoolNames() {
		return schoolRepository.getAllSchoolNames()
				.stream().map((school) -> new SchoolNameDTO(school.getId().toString(), school.getName())).collect(Collectors.toList());
	}

	@Override
	public Long getTotalNumberOfSchools() {
		return schoolRepository.count();
	}
}
