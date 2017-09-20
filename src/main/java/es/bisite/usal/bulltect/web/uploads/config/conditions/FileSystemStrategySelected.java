package es.bisite.usal.bulltect.web.uploads.config.conditions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;

public class FileSystemStrategySelected implements Condition {
    
    private static Logger logger = LoggerFactory.getLogger(FileSystemStrategySelected.class);

    private final static String STRATEGY_KEY = "upload.strategy";
    private final static String STRATEGY_NAME = "fileSystem";
   

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata arg1) {
        Environment environment = conditionContext.getEnvironment();
        Assert.notNull(environment, "Environment can not be null");
        String strategy = environment.getProperty(STRATEGY_KEY);
        logger.debug("Strategy -> " + strategy);
        return  strategy != null && !strategy.isEmpty() 
                    && strategy.equals(STRATEGY_NAME);
    }

}
