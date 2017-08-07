package sanchez.sanchez.sergio.service.impl;


import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.UserDTO;
import sanchez.sanchez.sergio.mapper.IParentEntityMapper;
import sanchez.sanchez.sergio.mapper.IUserEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.repository.ParentRepository;
import sanchez.sanchez.sergio.service.IParentsService;

@Service
public class ParentsServiceImpl implements IParentsService {

	private final ParentRepository parentRepository;
	private final IParentEntityMapper parentEntityMapper;
	private final IUserEntityMapper userEntityMapper;
	
	public ParentsServiceImpl(ParentRepository parentRepository, IParentEntityMapper parentEntityMapper,
			IUserEntityMapper userEntityMapper) {
		super();
		this.parentRepository = parentRepository;
		this.parentEntityMapper = parentEntityMapper;
		this.userEntityMapper = userEntityMapper;
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
	public Iterable<UserDTO> getChildrenOfParent(String id) {
		ParentEntity parentEntity = parentRepository.findOne(new ObjectId(id));
		return userEntityMapper.userEntitiesToUserDTOs(parentEntity.getChildren());
	}

}
