package es.bisite.usal.bulltect.persistence.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.AuthorityEntity;
import es.bisite.usal.bulltect.persistence.entity.AuthorityEnum;
import es.bisite.usal.bulltect.persistence.entity.DeviceGroupEntity;
import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.entity.SchoolEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.entity.UserSystemEntity;
import es.bisite.usal.bulltect.persistence.repository.AlertRepository;
import es.bisite.usal.bulltect.persistence.repository.AuthorityRepository;
import es.bisite.usal.bulltect.persistence.repository.DeviceGroupRepository;
import es.bisite.usal.bulltect.persistence.repository.ParentRepository;
import es.bisite.usal.bulltect.persistence.repository.SchoolRepository;
import es.bisite.usal.bulltect.persistence.repository.SocialMediaRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import es.bisite.usal.bulltect.persistence.repository.UserSystemRepository;

/**
 * @author sergio
 */

@Component
//@Profile("dev")
public class LoadInitialDataForDevelopmentEnvironment implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(LoadInitialDataForDevelopmentEnvironment.class);
    
    
    private final SchoolRepository schoolRepository;
    private final ParentRepository parentRepository;
    private final SonRepository sonRepository;
    private final SocialMediaRepository socialMediaRepository;
    private final AuthorityRepository authorityRepository;
    private final UserSystemRepository userSystemRepository;
    private final AlertRepository alertRepository;
    private final DeviceGroupRepository deviceGroupRepository;
    
    
    private static List<AuthorityEntity> authoritiesList = new ArrayList<>();
    private static List<SchoolEntity> schoolList = new ArrayList<>();
    private static List<ParentEntity> parentList = new ArrayList<>();
    private static List<SonEntity> childrenList = new ArrayList<>();
    private static List<SocialMediaEntity> socialMedias = new ArrayList<>();
    private static List<UserSystemEntity> admins = new ArrayList<>();
    private static List<AlertEntity> alertList = new ArrayList<>();
    private static List<DeviceGroupEntity> deviceGroupsList = new ArrayList<>();
   
    static {
    	
    	// Authorities
    	AuthorityEntity adminRole = new AuthorityEntity(AuthorityEnum.ROLE_ADMIN, "Role for Administrators");
    	
    	authoritiesList.add(adminRole);
    	
    	AuthorityEntity parentRole = new AuthorityEntity(AuthorityEnum.ROLE_PARENT, "Role for Parents");
    	
    	authoritiesList.add(parentRole);
    	
    	AuthorityEntity anonymousRole = new AuthorityEntity(AuthorityEnum.ROLE_ANONYMOUS, "Role for Anonymous");
    	
    	authoritiesList.add(anonymousRole);
    	
    	
    	// SCHOOL
    	SchoolEntity school1 = new SchoolEntity("C.E.I.P. Fernando Gavilán", "Avda. Herrera Oria, s/n", "Ubrique", "Cádiz", 956128801, "11008033.edu@juntadeandalucia.es");
    	
    	schoolList.add(school1);
    	
    	SchoolEntity school2 = new SchoolEntity("C.E.I.P. San Juan de Ávila", "C/ Erillas, s/n", "Iznalloz", "Granada", 958399591, "18005475.edu@juntadeandalucia.es");
    	
    	schoolList.add(school2);
    	
    	
    	// PARENTS
    	
    	Calendar parentsBirthdate = Calendar.getInstance();
    	parentsBirthdate.set(1982, 4, 23);
    	
    
    	ParentEntity federico = new ParentEntity("Federico", "Martín", parentsBirthdate.getTime(), "federico@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", parentRole);
    	
    	parentList.add(federico);
    	
    	ParentEntity fernando = new ParentEntity("Fernando", "Muñoz", parentsBirthdate.getTime(), "fernando@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", parentRole);
    	
    	parentList.add(fernando);
    	
    	ParentEntity jaime = new ParentEntity("Jaime", "Gómez", parentsBirthdate.getTime(), "jaime@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", parentRole);
    	
    	parentList.add(jaime);
    	
    	// ADMIN
    	
    	UserSystemEntity admin = new UserSystemEntity("Admin", "Admin", parentsBirthdate.getTime(), "admin@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", adminRole);
    	
    	admins.add(admin);
    	
    	// CHILDREN
    	
    	Calendar sergioBirthdate = Calendar.getInstance();
    	sergioBirthdate.set(2005, 4, 23);
    	
    	SonEntity sergio = new SonEntity("Sergio", "Martín", sergioBirthdate.getTime(), school1, federico);
  
    	childrenList.add(sergio);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("EAACEdEose0cBAC1jBbCWfkrLDHMjC6cciE1ZBEtZA9quybVlHy1IiBbeJyjTZAdXdmegRwNN06q59uEmhXgmEbsfeRgQgLy5v1TtwY8xt5FZC5G69Px2sgDPXd3mPPqBZC6fG9N78wUargPpz03Vr4mKl7ZC6RCbFC8byEksJuHenXfqnHlKVpkCZC3loaTbTlzJhQZBPV9muwZDZD", SocialMediaTypeEnum.FACEBOOK, sergio),
            new SocialMediaEntity("3303539559.5d2b345.6fb7b3f97e5142fd93973592ccc4c07d", SocialMediaTypeEnum.INSTAGRAM, sergio),
            new SocialMediaEntity("ya29.GmaiBCC-Gpm_NO9Z-Au4imXzsb9gFjBNYoO2QMEtfJlzYksM93pSZoWsr1yxFvRvI8wuNrLimC4KRf364TcE-ZGr3uyVdery8sQtw3ZKOUxyF1bPicG7lAVBmL113Ji3sK3hrnEpa0Y", SocialMediaTypeEnum.YOUTUBE, sergio)
        }));
        
        Calendar pedroBirthdate = Calendar.getInstance();
        pedroBirthdate.set(2008, 6, 4);
        
        SonEntity pedro = new SonEntity("Pedro", "Sánchez", pedroBirthdate.getTime(), school1, federico);
        
        childrenList.add(pedro);
          
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("pedro_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, pedro),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, pedro),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.YOUTUBE, pedro)
        }));
        
        Calendar maiteBirthdate = Calendar.getInstance();
        maiteBirthdate.set(2002, 1, 4);
        
        SonEntity maite =  new SonEntity("Maite", "Pérez", maiteBirthdate.getTime(), school1, federico);
        
        childrenList.add(maite);

        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("maite_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, maite),
            new SocialMediaEntity("maite_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, maite),
            new SocialMediaEntity("maite_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, maite)
        }));
        
        Calendar davidBirthdate = Calendar.getInstance();
        davidBirthdate.set(2002, 12, 24);
        
        SonEntity david =  new SonEntity("David", "García", davidBirthdate.getTime(), school2, fernando);
       
        childrenList.add(david);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("david_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, david),
            new SocialMediaEntity("david_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, david),
            new SocialMediaEntity("david_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, david)
        }));
        
        Calendar elenaBirthdate = Calendar.getInstance();
        elenaBirthdate.set(2003, 5, 14);
        
        SonEntity elena = new SonEntity("Elena", "Iglesias", elenaBirthdate.getTime(), school2, fernando);
        
        childrenList.add(elena);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("elena_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, elena),
            new SocialMediaEntity("elena_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, elena),
            new SocialMediaEntity("elena_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, elena)
        }));
        
        // Alerts
        
        AlertEntity alertInfo = new AlertEntity("Alerta de Prueba", federico, sergio);
        
        alertList.add(alertInfo);
        
        // Device Groups
        DeviceGroupEntity deviceGroup = new DeviceGroupEntity("notification-group-name", "notification-key", federico);
        
        deviceGroupsList.add(deviceGroup);
    
    }


    
    public LoadInitialDataForDevelopmentEnvironment(SchoolRepository schoolRepository, ParentRepository parentRepository,
			SonRepository sonRepository, SocialMediaRepository socialMediaRepository, AuthorityRepository authorityRepository, 
			UserSystemRepository userSystemRepository, AlertRepository alertRepository, DeviceGroupRepository deviceGroupRepository) {
		super();
		this.schoolRepository = schoolRepository;
		this.parentRepository = parentRepository;
		this.sonRepository = sonRepository;
		this.socialMediaRepository = socialMediaRepository;
		this.authorityRepository = authorityRepository;
		this.userSystemRepository = userSystemRepository;
		this.alertRepository = alertRepository;
		this.deviceGroupRepository = deviceGroupRepository;
	}
    
    
    @Override
    public void run(String...args) throws Exception {
        logger.debug("Delete all data");
        sonRepository.deleteAll();
        parentRepository.deleteAll();
        schoolRepository.deleteAll();
        socialMediaRepository.deleteAll();
        authorityRepository.deleteAll();
        userSystemRepository.deleteAll();
        alertRepository.deleteAll();
        deviceGroupRepository.deleteAll();
        logger.debug("Load Initial Data ...");
        authorityRepository.save(authoritiesList);
        schoolRepository.save(schoolList);
        parentRepository.save(parentList);
        sonRepository.save(childrenList);
        socialMediaRepository.save(socialMedias);
        userSystemRepository.save(admins);
        alertRepository.save(alertList);
        deviceGroupRepository.save(deviceGroupsList);
        logger.info("Data Loaded ...");
    }
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(schoolRepository, "SchoolRepository Can not be null");
    	Assert.notNull(parentRepository, "ParentRepository Can not be null");
    	Assert.notNull(sonRepository, "SonRepository Can not be null");
    	Assert.notNull(socialMediaRepository, "SocialMediaRepository Can not be null");
    	Assert.notNull(authorityRepository, "AuthorityRepository Can not be null");
    	Assert.notNull(userSystemRepository, "UserSystemRepository Can not be null");
    	Assert.notNull(alertRepository, "alertRepository Can not be null");
    	Assert.notNull(deviceGroupRepository, "deviceGroupRepository Can not be null");
    }

	
}
