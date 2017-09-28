package es.bisite.usal.bulltect.domain.service.impl;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.bisite.usal.bulltect.domain.service.ISchoolService;
import es.bisite.usal.bulltect.mapper.ISchoolEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.SchoolEntity;
import es.bisite.usal.bulltect.persistence.repository.SchoolRepository;
import es.bisite.usal.bulltect.web.dto.request.AddSchoolDTO;
import es.bisite.usal.bulltect.web.dto.response.SchoolDTO;

@Service
public class SchoolServiceImpl implements ISchoolService {
	
	private final ISchoolEntityMapper schoolEntityMapper;
	private final SchoolRepository schoolRepository;
	

	public SchoolServiceImpl(ISchoolEntityMapper schoolEntityMapper, SchoolRepository schoolRepository) {
		super();
		this.schoolEntityMapper = schoolEntityMapper;
		this.schoolRepository = schoolRepository;
	}

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

	@Override
	public Page<SchoolDTO> findByNamePaginated(String name, Pageable pageable) {
		Page<SchoolEntity> schoolPage = schoolRepository.findAllByNameLike(name, pageable);
		return schoolPage.map(new Converter<SchoolEntity, SchoolDTO>(){
            @Override
            public SchoolDTO convert(SchoolEntity school) {
               return schoolEntityMapper.schoolEntityToSchoolDTO(school);
            }
        });
	}

	@Override
	public SchoolDTO getSchoolById(String id) {
		SchoolEntity schoolEntity = schoolRepository.findOne(new ObjectId(id));
		return schoolEntityMapper.schoolEntityToSchoolDTO(schoolEntity);
	}

	@Override
	public SchoolDTO save(AddSchoolDTO addSchoolDTO) {
		SchoolEntity schoolToSave = schoolEntityMapper.addSchoolDTOToSchoolEntity(addSchoolDTO);
		SchoolEntity schoolSaved = schoolRepository.save(schoolToSave);
		return schoolEntityMapper.schoolEntityToSchoolDTO(schoolSaved);
	}

	@Override
	public SchoolDTO delete(String id) {
		SchoolEntity schoolToDelete = schoolRepository.findOne(new ObjectId(id));
		schoolRepository.delete(schoolToDelete);
		return schoolEntityMapper.schoolEntityToSchoolDTO(schoolToDelete);
	}

	@Override
	public Iterable<String> getAllSchoolNames() {
		return schoolRepository.getAllSchoolNames();
	}
}
