package sanchez.sanchez.sergio.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.mongodb.inbound.MongoDbMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import sanchez.sanchez.sergio.persistence.entity.UserEntity;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.service.IFacebookService;
import sanchez.sanchez.sergio.service.IInstagramService;
import sanchez.sanchez.sergio.service.IYoutubeService;

/**
 *
 * @author sergio
 */
@Configuration
@IntegrationComponentScan
public class InfrastructureConfiguration {
    
    private static Logger logger = LoggerFactory.getLogger(InfrastructureConfiguration.class);
    
    @Autowired
    private IFacebookService facebookService;
    
    @Autowired
    private IInstagramService instagramService;
    
    @Autowired
    private IYoutubeService youtubeService;
    
   
    /**
     * The Pollers builder factory can be used to configure common bean definitions or 
     * those created from IntegrationFlowBuilder EIP-methods
     */
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(10, TimeUnit.SECONDS)
                .get();
    }
    
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        return executor;
    }
    
    /**
     * MongoDbMessageSource is an instance of MessageSource which returns a Message with a payload 
     * which is the result of execution of a Query
     */
    @Bean
    @Autowired
    public MessageSource<Object> mongoMessageSource(MongoDbFactory mongo) {
        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongo, new LiteralExpression("{}"));
        messageSource.setExpectSingleResult(false);
        messageSource.setEntityClass(UserEntity.class);
        messageSource.setCollectionNameExpression(new LiteralExpression("users"));
        return messageSource;
    }
    
    @Bean
    public IntegrationFlow socialMediaErrorFlow() {
        return IntegrationFlows.from("socialMediaErrorChannel")
                .wireTap(sf -> sf.handle("errorService", "handleException"))
                .<MessagingException>handle((p, h)
                        -> MessageBuilder.withPayload(Collections.<CommentEntity>emptyList())
                        .copyHeaders(p.getFailedMessage().getHeaders())
                        .setHeader("ERROR", true)
                        .build()
                )
                .channel("directChannel_2")
                .get();
    }
    
    @Bean
    @Autowired
    public IntegrationFlow processUsers(MongoDbFactory mongo, PollerMetadata poller) {
        return IntegrationFlows.from(mongoMessageSource(mongo), c -> c.poller(poller))
                .<List<UserEntity>, Map<ObjectId, List<SocialMediaEntity>>>transform(userEntitiesList
                        -> userEntitiesList.stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getSocialMedia))
                )
                .split(new AbstractMessageSplitter() {
                    @Override
                    protected Object splitMessage(Message<?> msg) {
                        return ((Map<ObjectId, List<SocialMediaEntity>>) msg.getPayload()).entrySet();
                    }
                })
                .enrichHeaders(s -> 
                    s.headerExpressions(h -> h.put("user-id", "payload.key"))
                    .header(MessageHeaders.ERROR_CHANNEL, "socialMediaErrorChannel")
                )
                .channel("directChannel_1")
                .split(new AbstractMessageSplitter() {
                    @Override
                    protected Object splitMessage(Message<?> msg) {
                        return ((Entry<ObjectId, List<SocialMediaEntity>>) msg.getPayload()).getValue();
                    }
                })
                .channel(MessageChannels.executor("executorChannel", this.taskExecutor()))
                .<SocialMediaEntity, SocialMediaTypeEnum>route(p -> p.getType(),
                        m
                        -> m.subFlowMapping(SocialMediaTypeEnum.FACEBOOK, 
                                sf -> sf.handle(SocialMediaEntity.class, (p, h) -> facebookService.getComments(p.getAccessToken())))
                            .subFlowMapping(SocialMediaTypeEnum.YOUTUBE, 
                                sf -> sf.handle(SocialMediaEntity.class, (p, h) -> youtubeService.getComments(p.getAccessToken())))
                            .subFlowMapping(SocialMediaTypeEnum.INSTAGRAM, 
                                sf -> sf.handle(SocialMediaEntity.class, (p, h) -> instagramService.getComments(p.getAccessToken())))
                )
                .channel("directChannel_2")
                .aggregate()
                .channel("directChannel_3")
                .transform(new GenericTransformer<Message<List<List<CommentEntity>>>, List<CommentEntity>>() {
                    @Override
                    public List<CommentEntity> transform(Message<List<List<CommentEntity>>> message) {
                        ObjectId userId = (ObjectId)message.getHeaders().get("user-id");
                        List<CommentEntity> userComments = new ArrayList<>();
                        message.getPayload().stream().flatMap(List::stream).forEach(commentEntity -> {
                            commentEntity.setUser(userId);
                            userComments.add(commentEntity);
                        });
                        return userComments;
                    }
                })
                .aggregate()
                .channel("directChannel_4")
                .<List<List<CommentEntity>>, List<CommentEntity>>transform(comments -> 
                        comments.stream().flatMap(List::stream).collect(Collectors.toList()))
                .channel("storeChannel")
                .filter(new GenericSelector<List<CommentEntity>> () {
                    @Override
                    public boolean accept(List<CommentEntity> commentEntities) {
                        return commentEntities.size() > 0;
                    }
                })
                .handle("commentService", "saveComments")
                .get();
    }
    
    
    @PostConstruct
    protected void init(){
        Assert.notNull(facebookService, "The Facebook Service can not be null");
        Assert.notNull(instagramService, "The Instagram Service can not be null");
        Assert.notNull(youtubeService, "The Youtube Service can not be null");
    }
    
}
