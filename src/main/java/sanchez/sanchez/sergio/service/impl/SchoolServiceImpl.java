package sanchez.sanchez.sergio.service.impl;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.mapper.ISchoolEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.SchoolEntity;
import sanchez.sanchez.sergio.persistence.repository.SchoolRepository;
import sanchez.sanchez.sergio.service.ISchoolService;

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
		Page<SchoolEntity> schoolPage = schoolRepository.findAllByName(name, pageable);
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
}
