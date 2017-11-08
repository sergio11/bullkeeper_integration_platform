package es.bisite.usal.bulltect.integration.config;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.mongodb.inbound.MongoDbMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.*;
import javax.annotation.PostConstruct;

import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.exception.IntegrationFlowException;
import es.bisite.usal.bulltect.integration.aggregation.IterationGroupProcessor;
import es.bisite.usal.bulltect.integration.constants.IntegrationConstants;
import es.bisite.usal.bulltect.integration.properties.IntegrationFlowProperties;
import es.bisite.usal.bulltect.integration.transformer.IterationEntityTransformer;
import es.bisite.usal.bulltect.integration.transformer.TaskEntityTransformer;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.rrss.service.IFacebookService;
import es.bisite.usal.bulltect.rrss.service.IInstagramService;
import es.bisite.usal.bulltect.rrss.service.IYoutubeService;

/**
 * @author sergio
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
@Profile({"dev", "prod"})
public class InfrastructureConfiguration {
    
    private static Logger logger = LoggerFactory.getLogger(InfrastructureConfiguration.class);
    
   
    @Autowired
    private IFacebookService facebookService;
    
    @Autowired
    private IInstagramService instagramService;
    
    @Autowired
    private IYoutubeService youtubeService;
    
    @Autowired
    private IntegrationFlowProperties integrationFlowProperties;
    
    @Autowired
    private IterationEntityTransformer iterationEntityTransformer;
    
   
    /**
     * The Pollers builder factory can be used to configure common bean definitions or 
     * those created from IntegrationFlowBuilder EIP-methods
     */
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedDelay(integrationFlowProperties.getFlowFixedDelay(), TimeUnit.SECONDS).get();
    }
    
    @Bean
    public TaskExecutor integrationTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("integration-pool-thread");
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
        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongo, new SpelExpressionParser()
    			.parseExpression("new BasicQuery(\" { 'invalid_token' : false, 'scheduled_for' : { '$lte' : \" +  new java.util.Date().getTime()  + \" } } \")"));
        messageSource.setExpectSingleResult(false);
        messageSource.setEntityClass(SocialMediaEntity.class);
        messageSource.setCollectionNameExpression(new LiteralExpression(SocialMediaEntity.COLLECTION_NAME));
        return messageSource;
    }
    
    
    @Bean
    public SubscribableChannel iterationFinishedPubSubChannel()
    {
        return MessageChannels
        		.publishSubscribe("iterationFinishedPubSubChannel")
        		.get();
    }
    
    /**
     * Flow to handle errors produced when getting comments
     * @return
     */
    @Bean
    public IntegrationFlow socialMediaErrorFlow() {
        return IntegrationFlows.from("socialMediaErrorChannel")
        		.<MessagingException, MessagingException>transform(p -> {
        			IntegrationFlowException integrationException = (IntegrationFlowException)p.getCause();
        			integrationException.setTarget((SonEntity)p.getFailedMessage().getHeaders().get(IntegrationConstants.USER_HEADER));
        			return p;
        		})
                .wireTap(sf -> sf.handle("errorService", "handleException"))
                .<MessagingException>handle((p, h)
                        -> MessageBuilder.withPayload(Collections.<CommentEntity>emptySet())
                        .copyHeaders(p.getFailedMessage().getHeaders())
                        .setHeader(IntegrationConstants.TASK_ERROR_HEADER, true)
                        .build()
                )
                .channel("directChannel_1")
                .get();
    }
    
    /**
     * social media planning for the next iteration
     * @return
     */
    @Bean
    public IntegrationFlow scheduleSocialMediaForNextIteration() {
        return IntegrationFlows.from(iterationFinishedPubSubChannel())
           .handle("integrationFlowService", "scheduleSocialMediaForNextIteration")
           .get();
    }
    
    
    /**
     *  Generating alerts for the current iteration
     * @return
     */
    @Bean
    public IntegrationFlow generateAlertsForIteration() {
        return IntegrationFlows.from(iterationFinishedPubSubChannel())
           .handle("integrationFlowService", "generateAlertsForIteration")
           .get();
    }
    
    
    /**
     * Mainstream, obtains valid social media and proceeds to obtain comments using the access token
     * @param mongo
     * @param poller
     * @return
     */
    @Bean
    @Autowired
    public IntegrationFlow processUsers(MongoDbFactory mongo, PollerMetadata poller) {
        return IntegrationFlows.from(mongoMessageSource(mongo), c -> c.poller(poller))
                .enrichHeaders(s -> s.header(IntegrationConstants.ITERATION_START_HEADER, new Date()))
                .split()
                .enrichHeaders(s -> 
                    s.headerExpressions(h -> 
                    	h.put(IntegrationConstants.USER_HEADER, "payload.sonEntity")
                    		.put(IntegrationConstants.SOCIAL_MEDIA_ID_HEADER, "payload.id")
                    )
                    .header(MessageHeaders.ERROR_CHANNEL, "socialMediaErrorChannel")
                    .header(IntegrationConstants.TASK_START_HEADER, new Date())
                )
                .channel(MessageChannels.executor("executorChannel", this.integrationTaskExecutor()))
                .<SocialMediaEntity, SocialMediaTypeEnum>route(p -> p.getType(),
                        m
                        -> m.subFlowMapping(SocialMediaTypeEnum.FACEBOOK, 
                                sf -> sf.handle(SocialMediaEntity.class, (p, h) -> facebookService.getCommentsLaterThan(p.getLastProbing(), p.getAccessToken())))
                            .subFlowMapping(SocialMediaTypeEnum.YOUTUBE, 
                                sf -> sf.handle(SocialMediaEntity.class, (p, h) -> youtubeService.getCommentsLaterThan(p.getLastProbing(), p.getAccessToken())))
                            .subFlowMapping(SocialMediaTypeEnum.INSTAGRAM, 
                                sf -> sf.handle(SocialMediaEntity.class, (p, h) -> instagramService.getCommentsLaterThan(p.getLastProbing(), p.getAccessToken())))
                )
                .channel("directChannel_1")
                .transform(new TaskEntityTransformer())
                .aggregate(a -> a.outputProcessor(new IterationGroupProcessor()))
                .channel("directChannel_2")
                .transform(iterationEntityTransformer)
                .channel(iterationFinishedPubSubChannel())
                .get();
    }
    
    
    @PostConstruct
    protected void init(){
        Assert.notNull(facebookService, "The Facebook Service can not be null");
        Assert.notNull(instagramService, "The Instagram Service can not be null");
        Assert.notNull(youtubeService, "The Youtube Service can not be null");
       
        logger.debug("Init InfrastructureConfiguration ....");
        
    }
    
}
