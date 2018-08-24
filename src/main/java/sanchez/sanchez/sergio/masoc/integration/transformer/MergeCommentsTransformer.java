package sanchez.sanchez.sergio.masoc.integration.transformer;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.masoc.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.CommentRepository;

@Component
public class MergeCommentsTransformer implements GenericTransformer<Message<Set<CommentEntity>>, Set<CommentEntity>> {
	
	private Logger logger = LoggerFactory.getLogger(MergeCommentsTransformer.class);
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Set<CommentEntity> transform(Message<Set<CommentEntity>> message) {
		
		logger.debug("Merge comments");
		
		final Set<CommentEntity> comments = message.getPayload();
		
		final Set<String> externalIds = comments.parallelStream()
				.map(comment -> comment.getExternalId()).collect(Collectors.toSet());
		
		if(!externalIds.isEmpty()){
			
			final Set<CommentEntity> commentsSaved = commentRepository.findAllByExternalIdIn(externalIds);
			
			if(!commentsSaved.isEmpty()) {
				
				final Iterator<CommentEntity> commentIterator = comments.iterator();
				while (commentIterator.hasNext()) {
				    final CommentEntity comment = commentIterator.next();
				    final CommentEntity commentSavedVersion = commentsSaved.stream().filter(comment::equals).findAny().orElse(null);
				    if(commentSavedVersion != null) {
				    	// There is a saved version of the comment
				    	
				    	if(!commentSavedVersion.getLikes().equals(comment.getLikes()) 
				    			|| !commentSavedVersion.getMessage().equalsIgnoreCase(comment.getMessage())){
				    		// The comment has changed with respect to its saved version
				    		comment.setId(commentSavedVersion.getId());
				    		comment.setExtractedTimes(commentSavedVersion.getExtractedTimes() + 1);
				    	} else {
				    		// The comment has not changed with respect to its guardad version, we discard it.
				    		commentIterator.remove();
				    	}
				    	
				    }
					
				}
			}
		}
		
		return comments;
	}

}
