package sanchez.sanchez.sergio.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sanchez.sanchez.sergio.dto.response.CommentsBySon;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.mapper.SonEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.persistence.repository.IterationRepository;
import sanchez.sanchez.sergio.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.persistence.repository.impl.SocialMediaRepositoryImpl;
import sanchez.sanchez.sergio.service.ISonService;

@Service
public class SonServiceImpl implements ISonService {
	
	private static Logger logger = LoggerFactory.getLogger(SonServiceImpl.class);
	
	private final SonRepository sonRepository;
	private final SonEntityMapper sonEntityMapper;
	private final IterationRepository iterationRepository;
	
	public SonServiceImpl(SonRepository sonRepository, SonEntityMapper sonEntityMapper, IterationRepository iterationRepository) {
		super();
		this.sonRepository = sonRepository;
		this.sonEntityMapper = sonEntityMapper;
		this.iterationRepository = iterationRepository;
	}

	@Override
	public Page<SonDTO> findPaginated(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
        Page<SonEntity> childrenPage = sonRepository.findAll(pageable);
        return childrenPage.map(new Converter<SonEntity, SonDTO>(){
            @Override
            public SonDTO convert(SonEntity sonEntity) {
               return sonEntityMapper.sonEntityToSonDTO(sonEntity);
            }
        });
	}

	@Override
	public Page<SonDTO> findPaginated(Pageable pageable) {
		Page<SonEntity> childrenPage = sonRepository.findAll(pageable);
        return childrenPage.map(new Converter<SonEntity, SonDTO>(){
            @Override
            public SonDTO convert(SonEntity sonEntity) {
               return sonEntityMapper.sonEntityToSonDTO(sonEntity);
            }
        });
	}

	@Override
	public SonDTO getSonById(String id) {
		SonEntity sonEntity = sonRepository.findOne(new ObjectId(id));
		return sonEntityMapper.sonEntityToSonDTO(sonEntity);
	}
	

	@Override
	public Long getTotalChildren() {
		return sonRepository.count();
	}
}
