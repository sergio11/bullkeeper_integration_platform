package sanchez.sanchez.sergio.config;

import java.util.Collections;
import java.util.Date;
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
import java.util.concurrent.*;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.persistence.entity.TaskEntity;
import sanchez.sanchez.sergio.service.IFacebookService;
import sanchez.sanchez.sergio.service.IInstagramService;
import sanchez.sanchez.sergio.service.IYoutubeService;

/**
 *
 * @author sergio
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class InfrastructureConfiguration {
    
    private static Logger logger = LoggerFactory.getLogger(InfrastructureConfiguration.class);
    
    private static String USER_HEADER = "user";
    private static String TASK_START_HEADER = "taskStart";
    private static String ITERATION_START_HEADER = "iterationStart";
    private static String TASK_ERROR_HEADER = "taskError";
    
    @Autowired
    private IFacebookService facebookService;
    
    @Autowired
    private IInstagramService instagramService;
    
    @Autowired
    private IYoutubeService youtubeService;
    
    @Value("${poller.integration.flow.time}")
    private Integer pollerTime;
   
    /**
     * The Pollers builder factory can be used to configure common bean definitions or 
     * those created from IntegrationFlowBuilder EIP-methods
     */
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(pollerTime, TimeUnit.SECONDS).get();
    }
    
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
    
    /**
     * MongoDbMessageSource is an instance of MessageSource which returns a Message with a payload 
     * which is the result of execution of a Query
     */
    @Bean
    @Autowired
    public MessageSource<Object> mongoMessageSource(MongoDbFactory mongo) {
        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongo, new LiteralExpression("{ 'invalid_token' : false }"));
        messageSource.setExpectSingleResult(false);
        messageSource.setEntityClass(SocialMediaEntity.class);
        messageSource.setCollectionNameExpression(new LiteralExpression(SocialMediaEntity.COLLECTION_NAME));
        return messageSource;
    }
    
    @Bean
    public IntegrationFlow socialMediaErrorFlow() {
        return IntegrationFlows.from("socialMediaErrorChannel")
                .wireTap(sf -> sf.handle("errorService", "handleException"))
                .<MessagingException>handle((p, h)
                        -> MessageBuilder.withPayload(Collections.<CommentEntity>emptyList())
                        .copyHeaders(p.getFailedMessage().getHeaders())
                        .setHeader(TASK_ERROR_HEADER, true)
                        .build()
                )
                .channel("directChannel_1")
                .get();
    }
    
    @Bean
    @Autowired
    public IntegrationFlow processUsers(MongoDbFactory mongo, PollerMetadata poller) {
        return IntegrationFlows.from(mongoMessageSource(mongo), c -> c.poller(poller))
                .enrichHeaders(s -> s.header(ITERATION_START_HEADER, new Date()))
                .split()
                .enrichHeaders(s -> 
                    s.headerExpressions(h -> h.put(USER_HEADER, "payload.sonEntity"))
                    .header(MessageHeaders.ERROR_CHANNEL, "socialMediaErrorChannel")
                    .header(TASK_START_HEADER, new Date())
                )
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
                .channel("directChannel_1")
                .transform(new GenericTransformer<Message<List<CommentEntity>>, TaskEntity>() {
                    @Override
                    public TaskEntity transform(Message<List<CommentEntity>> message) {
                    	SonEntity sonEntity = (SonEntity)message.getHeaders().get(USER_HEADER);
                        Date taskStart = (Date)message.getHeaders().get(TASK_START_HEADER);
                        Boolean isSuccess = !message.getHeaders().containsKey(TASK_ERROR_HEADER);
                        List<CommentEntity> comments = message.getPayload();
                        for(CommentEntity comment: comments) {
                            comment.setSonEntity(sonEntity);
                        }
                        return new TaskEntity(taskStart, new Date(), isSuccess, comments, sonEntity);
                    }
                })
                .aggregate(a -> a.outputProcessor(new MessageGroupProcessor() {
                    @Override
                    public Object processMessageGroup(MessageGroup mg) {
                        Date iterationStart = (Date)mg.getOne().getHeaders().get(ITERATION_START_HEADER);
                        IterationEntity iterationEntity = new IterationEntity(iterationStart, new Date());
                        iterationEntity.setTotalTasks(mg.getMessages().size());
                        for(Message<?> message: mg.getMessages()){
                            TaskEntity task = (TaskEntity)message.getPayload();
                            if(task.isSuccess())
                                iterationEntity.setTotalComments(iterationEntity.getTotalComments() + task.getComments().size());
                            else
                                iterationEntity.setTotalFailedTasks(iterationEntity.getTotalFailedTasks() + 1);
                            iterationEntity.addTask(task);
                            
                        }
                        return iterationEntity;
                    }
                }))
                .channel("directChannel_2")
                .handle("iterationService", "save")
                .get();
    }
    
    
    @PostConstruct
    protected void init(){
        Assert.notNull(facebookService, "The Facebook Service can not be null");
        Assert.notNull(instagramService, "The Instagram Service can not be null");
        Assert.notNull(youtubeService, "The Youtube Service can not be null");
    }
    
}
