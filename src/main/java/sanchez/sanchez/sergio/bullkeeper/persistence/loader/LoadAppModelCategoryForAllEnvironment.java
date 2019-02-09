package sanchez.sanchez.sergio.bullkeeper.persistence.loader;

import java.io.InputStreamReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAppModelCategoryService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAppModelService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelCategoryDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppModelDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelCategoryDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppModelDTO;

/**
 * Load App Model Category For All Environment
 * @author sergiosanchezsanchez
 *
 */
@Component("loadAppModelCategory")
public final class LoadAppModelCategoryForAllEnvironment implements CommandLineRunner {
	
	
	private static final Logger logger = LoggerFactory.getLogger(LoadAppModelCategoryForAllEnvironment.class);

	/**
	 * Object Mapper
	 */
	private final ObjectMapper objectMapper;
	
	/**
	 * App Model Category Service
	 */
	private final IAppModelCategoryService appModelCategoryService;
	
	/**
	 * App Categories Dump
	 */
	@Value("classpath:app_categories.json")
    private Resource appCategoriesDump;
	
	/**
	 * 
	 * @param objectMapper
	 */
	@Autowired
	public LoadAppModelCategoryForAllEnvironment(final ObjectMapper objectMapper,
			final IAppModelCategoryService appModelCategoryService) {
		this.objectMapper = objectMapper;
		this.appModelCategoryService = appModelCategoryService;
	}
	
	/**
	 * 
	 */
	@Override
	public void run(String... args) throws Exception {
		
		try {
		
			TypeReference<List<SaveAppModelCategoryDTO>> typeReference = 
					new TypeReference<List<SaveAppModelCategoryDTO>>(){};
					
			final InputStreamReader inputStreamReader = 
					new InputStreamReader(appCategoriesDump.getInputStream(), "UTF-8");
			
			// Read App Model Category list
			final List<SaveAppModelCategoryDTO> appModelCategoryList = 
					objectMapper.readValue(inputStreamReader, typeReference);
			
			// Save App Models
			final Iterable<AppModelCategoryDTO> appModelCategoryDTOList = appModelCategoryService.save(appModelCategoryList);
			
			logger.debug("App Model Category Save size -> " + Iterables.size(appModelCategoryDTOList));
			
		} catch (final Exception ex) {
			ex.printStackTrace();
			logger.debug("Load App Models failed ...");
			
		}

	}

}
