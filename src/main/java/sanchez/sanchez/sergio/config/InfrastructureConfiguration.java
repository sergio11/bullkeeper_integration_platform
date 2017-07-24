package sanchez.sanchez.sergio.config;

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
import org.bson.types.ObjectId;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.GenericHandler;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;

/**
 *
 * @author sergio
 */
@Configuration
@IntegrationComponentScan
public class InfrastructureConfiguration {
    
    private static Logger logger = LoggerFactory.getLogger(InfrastructureConfiguration.class);
    
    /**
     * The Pollers builder factory can be used to configure common bean definitions or 
     * those created from IntegrationFlowBuilder EIP-methods
     */
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(10, TimeUnit.SECONDS).get();
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
     * 
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
                .channel("directChannel_1")
                .enrichHeaders(s -> s.headerExpressions(h -> h.put("user-id", "payload.key")))
                .split(new AbstractMessageSplitter() {
                    @Override
                    protected Object splitMessage(Message<?> msg) {
                        return ((Entry<ObjectId, List<SocialMediaEntity>>) msg.getPayload()).getValue();
                    }
                })
                .channel(MessageChannels.executor("executorChannel", this.taskExecutor()))
                .<SocialMediaEntity, SocialMediaTypeEnum>route(p -> p.getType(),
                        m
                        -> m.subFlowMapping(SocialMediaTypeEnum.FACEBOOK, sf -> sf.handle(new GenericHandler<SocialMediaEntity>() {
                                @Override
                                public Object handle(SocialMediaEntity payload, Map<String, Object> headers) {
                                    logger.info("TEST FACEBOOK Channel for user id: " + headers.get("user-id"));
                                    return payload;
                                }
                            }))
                            .subFlowMapping(SocialMediaTypeEnum.YOUTUBE, sf -> sf.handle(new GenericHandler<SocialMediaEntity>() {
                                @Override
                                public Object handle(SocialMediaEntity payload, Map<String, Object> headers) {
                                    logger.info("TEST YOUTUBE Channel for user id: " + headers.get("user-id"));
                                    return payload;
                                }
                            }))
                            .subFlowMapping(SocialMediaTypeEnum.INSTAGRAM, sf -> sf.handle(new GenericHandler<SocialMediaEntity>() {
                                @Override
                                public Object handle(SocialMediaEntity payload, Map<String, Object> headers) {
                                    logger.info("TEST INSTAGRAM Channel for user id: " + headers.get("user-id"));
                                    return payload;
                                }
                            }))
                )
                .channel("directChannel_2")
                .aggregate()
                .channel("directChannel_3")
                .wireTap(sf -> sf.handle(message -> 
                    logger.info("Finish Social Media for user  "  + message.getHeaders().get("user-id"))
                ))
                .aggregate()
                .channel("directChannel_4")
                .handle(usersEntity -> 
                    logger.info("users:" + usersEntity.getPayload() + " on thread "
                        + Thread.currentThread().getName())
                )
                .get();
    }
    
}
