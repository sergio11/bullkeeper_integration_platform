package sanchez.sanchez.sergio.bullkeeper.persistence.loader;

import java.io.InputStreamReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IAppModelService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelDTO;

/**
 * Load App Model For All Environment
 * @author sergiosanchezsanchez
 *
 */
@Component
@DependsOn("loadAppModelCategory")
public final class LoadAppModelForAllEnvironment implements CommandLineRunner {
	
	
	private static final Logger logger = LoggerFactory.getLogger(LoadAppModelForAllEnvironment.class);

	/**
	 * Object Mapper
	 */
	private final ObjectMapper objectMapper;
	
	/**
	 * App Model Service
	 */
	private final IAppModelService appModelService;
	
	/**
	 * App Model Dump
	 */
	@Value("classpath:app_models_dump.json")
    private Resource appModelDump;
	
	/**
	 * 
	 * @param objectMapper
	 */
	@Autowired
	public LoadAppModelForAllEnvironment(final ObjectMapper objectMapper,
			final IAppModelService appModelService) {
		this.objectMapper = objectMapper;
		this.appModelService = appModelService;
	}
	
	/**
	 * 
	 */
	@Override
	public void run(String... args) throws Exception {
		
		try {
		
			TypeReference<List<SaveAppModelDTO>> typeReference = 
					new TypeReference<List<SaveAppModelDTO>>(){};
					
			final InputStreamReader inputStreamReader = 
					new InputStreamReader(appModelDump.getInputStream(), "UTF-8");
			
			// Read App Model list
			final List<SaveAppModelDTO> appModelList = 
					objectMapper.readValue(inputStreamReader, typeReference);
			
			// Save App Models
			final Iterable<AppModelDTO> appModelDTOList = appModelService.save(appModelList);
			
			logger.debug("App Model Save size -> " + Iterables.size(appModelDTOList));
			
		} catch (final Exception ex) {
			ex.printStackTrace();
			logger.debug("Load App Models failed ...");
		}

	}

}
