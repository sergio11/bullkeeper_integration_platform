package sanchez.sanchez.sergio.service.impl;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.mapper.ParentEntityMapper;
import sanchez.sanchez.sergio.mapper.SonEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.persistence.repository.ParentRepository;
import sanchez.sanchez.sergio.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.service.IParentsService;

@Service
public class ParentsServiceImpl implements IParentsService {

	private final ParentRepository parentRepository;
	private final ParentEntityMapper parentEntityMapper;
	private final SonEntityMapper  sonEntityMapper;
	private final SonRepository sonRepository;
	
	public ParentsServiceImpl(ParentRepository parentRepository, ParentEntityMapper parentEntityMapper, SonEntityMapper sonEntityMapper, SonRepository sonRepository) {
		super();
		this.parentRepository = parentRepository;
		this.parentEntityMapper = parentEntityMapper;
		this.sonEntityMapper = sonEntityMapper;
		this.sonRepository = sonRepository;
	}

	@Override
	public Page<ParentDTO> findPaginated(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size);
        Page<ParentEntity> parentsPage = parentRepository.findAll(pageable);
        return parentsPage.map(new Converter<ParentEntity, ParentDTO>(){
            @Override
            public ParentDTO convert(ParentEntity parent) {
               return parentEntityMapper.parentEntityToParentDTO(parent);
            }
        });
	}

	@Override
	public Page<ParentDTO> findPaginated(Pageable pageable) {
		Page<ParentEntity> parentsPage = parentRepository.findAll(pageable);
        return parentsPage.map(new Converter<ParentEntity, ParentDTO>(){
            @Override
            public ParentDTO convert(ParentEntity parent) {
               return parentEntityMapper.parentEntityToParentDTO(parent);
            }
        });
	}

	@Override
	public ParentDTO getParentById(String id) {
		ParentEntity parentEntity = parentRepository.findOne(new ObjectId(id));
        return parentEntityMapper.parentEntityToParentDTO(parentEntity);
	}

	@Override
	public Iterable<SonDTO> getChildrenOfParent(String id) {
		Iterable<SonEntity> children = sonRepository.findByParentId(new ObjectId(id));
		return sonEntityMapper.sonEntitiesToSonDTOs(children);
	}

	@Override
	public ParentDTO save(RegisterParentDTO registerParent) {
		final ParentEntity parentToSave = parentEntityMapper.registerParentDTOToParentEntity(registerParent);
		final ParentEntity parentSaved = parentRepository.save(parentToSave);
		return parentEntityMapper.parentEntityToParentDTO(parentSaved);
	}

	@Override
	public SonDTO addSon(String parentId, RegisterSonDTO registerSonDTO) {
		SonEntity sonToAdd = sonEntityMapper.registerSonDTOToSonEntity(registerSonDTO);
		ParentEntity parentEntity = parentRepository.findOne(new ObjectId(parentId));
		sonToAdd.setParent(parentEntity);
		parentRepository.save(parentEntity);
		return sonEntityMapper.sonEntityToSonDTO(sonToAdd);
	}

	@Override
	public ParentDTO getParentById(ObjectId id) {
		ParentEntity parentEntity = parentRepository.findOne(id);
        return parentEntityMapper.parentEntityToParentDTO(parentEntity);
	}

}
