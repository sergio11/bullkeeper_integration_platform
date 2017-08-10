package sanchez.sanchez.sergio.config.rest;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.web.PagedResourcesAssembler;

import io.swagger.annotations.Api;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {
	
	@Value("${jwt.token.header}")
	private String tokenHeaderName;
   
    /**
     * Create Swagger Api configuration
     * @return Swagger Docket
     */
    @Bean
    public Docket sadrApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("bullyTect")
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(PagedResourcesAssembler.class)
                .ignoredParameterTypes(CommonUserDetailsAware.class)
                .globalOperationParameters(provideGlobalParameters())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false);
    }
    
    @Bean
    public List<Parameter> provideGlobalParameters(){
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name(tokenHeaderName).modelRef(new ModelRef("string"))
        	.parameterType("header")
        	.description("Authorization Header")
        	.required(false)
        	.build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
    	return aParameters;
    }

    /**
     * Generate Api Info
     * @return Swagger API Info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Bully Tect REST API")
                .description("REST API for Bully Tect")
                .version("0.0.1-SNAPSHOT")
                .license("Open source licensing")
                .licenseUrl("https://help.github.com/articles/open-source-licensing/")
                .build();
    }
}