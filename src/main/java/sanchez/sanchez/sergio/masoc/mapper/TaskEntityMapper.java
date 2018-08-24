package sanchez.sanchez.sergio.masoc.mapper;

import java.util.Set;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.entity.TaskEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.masoc.web.dto.response.TaskDTO;

@Mapper
public abstract class TaskEntityMapper {
	
	@Autowired
	protected SonEntityMapper sonEntityMapper;
	
	@Autowired
	protected SocialMediaRepository socialMediaRepository;
	
	@Autowired
	protected CommentEntityMapper commentEntityMapper;
	
	@Mappings({
		@Mapping(expression="java(taskEntity.getId().toString())", target = "identity" ),
        @Mapping(source = "taskEntity.startDate", target = "startDate", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(source = "taskEntity.finishDate", target = "finishDate", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(expression="java(commentEntityMapper.commentEntitiesToCommentDTOs(taskEntity.getComments()))", target = "comments"),
        @Mapping(expression="java(taskEntity.getComments().size())", target = "totalComments"),
        @Mapping(expression="java(sonEntityMapper.sonEntityToSonDTO(taskEntity.getSonEntity()))", target = "son"),
        @Mapping(expression="java(taskEntity.getSocialMediaId().toString())", target = "socialMediaId")
        //@Mapping(expression="java(socialMediaRepository.getSocialMediaTypeById(taskEntity.getSocialMediaId()))", target = "socialMediaType")
    })
    @Named("taskEntityToTaskDTO")
    public abstract TaskDTO taskEntityToTaskDTO(TaskEntity taskEntity); 
	
    @IterableMapping(qualifiedByName = "taskEntityToTaskDTO")
    public abstract Set<TaskDTO> taskEntitiesToTaskDTOs(Set<TaskEntity> taskEntities);

}
