package sanchez.sanchez.sergio.masoc.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import com.restfb.types.Post;

import sanchez.sanchez.sergio.masoc.persistence.entity.CommentEntity;

@Mapper
public interface IFacebookPostMapper {
	
	@Mappings({ 
    	@Mapping(target = "id", ignore=true),
        @Mapping(source = "facebookPost.id", target="externalId"),
        @Mapping(source = "facebookPost.likesCount", target = "likes"),
        @Mapping(expression="java(sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum.FACEBOOK)", target = "socialMedia"),
        @Mapping(source = "facebookPost.from.name", target = "author.name"),
        @Mapping(source = "facebookPost.from.id", target = "author.externalId"),
        @Mapping(expression="java(String.format(\"https://graph.facebook.com/v2.5/%s/picture?width=300&height=300\", facebookPost.getFrom().getId()))", target="author.image")
    })
    @Named("facebookPostToCommentEntity")
    CommentEntity facebookPostToCommentEntity(Post facebookPost); 
	
    @IterableMapping(qualifiedByName = "facebookPostToCommentEntity")
    List<CommentEntity> facebookPostToCommentEntities(List<Post> facebookPosts);

}
