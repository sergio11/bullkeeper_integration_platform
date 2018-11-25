package sanchez.sanchez.sergio.bullkeeper.persistence.loader;

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

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AuthorityEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AuthorityEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DeviceGroupEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SchoolEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AuthorityRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.DeviceGroupRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SchoolRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.UserSystemRepository;

/**
 * @author sergio
 */

@Component
@Profile("dev")
public class LoadInitialDataForDevelopmentEnvironment implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(LoadInitialDataForDevelopmentEnvironment.class);
    
    
    /**
     * 
     */
    private final SchoolRepository schoolRepository;
    private final GuardianRepository guardianRepository;
    private final KidRepository kidRepository;
    private final SocialMediaRepository socialMediaRepository;
    private final AuthorityRepository authorityRepository;
    private final UserSystemRepository userSystemRepository;
    private final AlertRepository alertRepository;
    private final DeviceGroupRepository deviceGroupRepository;
    private final CommentRepository commentRepository;
    private final SupervisedChildrenRepository supervisedChildrenRepository;
    
    
    private static List<AuthorityEntity> authoritiesList = new ArrayList<>();
    private static List<SchoolEntity> schoolList = new ArrayList<>();
    private static List<GuardianEntity> guardianList = new ArrayList<>();
    private static List<KidEntity> childrenList = new ArrayList<>();
    private static List<SocialMediaEntity> socialMedias = new ArrayList<>();
    private static List<UserSystemEntity> admins = new ArrayList<>();
    private static List<AlertEntity> alertList = new ArrayList<>();
    private static List<DeviceGroupEntity> deviceGroupsList = new ArrayList<>();
    private static List<SupervisedChildrenEntity> supervisedChildrenList = new ArrayList<>();
   
    static {
    	
    	// Authorities
    	AuthorityEntity adminRole = new AuthorityEntity(AuthorityEnum.ROLE_ADMIN, "Role for Administrators");
    	
    	authoritiesList.add(adminRole);
    	
    	AuthorityEntity guardianRole = new AuthorityEntity(AuthorityEnum.ROLE_GUARDIAN, "Role for Guardians");
    	
    	authoritiesList.add(guardianRole);
    	
    	AuthorityEntity anonymousRole = new AuthorityEntity(AuthorityEnum.ROLE_ANONYMOUS, "Role for Anonymous");
    	
    	authoritiesList.add(anonymousRole);
    	
    	
    	// SCHOOL
    	SchoolEntity school1 = new SchoolEntity("C.E.I.P. Fernando Gavilán", "Avda. Herrera Oria, s/n", "Cádiz", 956128801, "11008033.edu@juntadeandalucia.es");
    	
    	schoolList.add(school1);
    	
    	SchoolEntity school2 = new SchoolEntity("C.E.I.P. San Juan de Ávila", "C/ Erillas, s/n", "Granada", 958399591, "18005475.edu@juntadeandalucia.es");
    	
    	schoolList.add(school2);
    	
    	
    	// Guardians
    	
    	Calendar guardiansBirthdate = Calendar.getInstance();
    	guardiansBirthdate.set(1982, 4, 23);
    	
    
    	GuardianEntity federico = new GuardianEntity("Federico", "Martín", guardiansBirthdate.getTime(),
    			"federico@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", 
    			guardianRole);
    	
    	guardianList.add(federico);
    	
    	GuardianEntity fernando = new GuardianEntity("Fernando", "Muñoz", guardiansBirthdate.getTime(),
    			"fernando@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu",
    			guardianRole);
    	
    	guardianList.add(fernando);
    	
    	GuardianEntity jaime = new GuardianEntity("Jaime", "Gómez", guardiansBirthdate.getTime(),
    			"jaime@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", guardianRole);
    	
    	guardianList.add(jaime);
    	
    	// ADMIN
    	
    	UserSystemEntity admin = new UserSystemEntity("Admin", "Admin", guardiansBirthdate.getTime(), "admin@gmail.com", "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", adminRole);
    	
    	admins.add(admin);
    	
    	// CHILDREN
    	
    	
    	
    	/**
    	 *
    	 */
    	Calendar sergioBirthdate = Calendar.getInstance();
    	sergioBirthdate.set(2005, 4, 23);
    	
    	KidEntity sergio = new KidEntity("Sergio", "Martín", sergioBirthdate.getTime(), school1);
  
    	childrenList.add(sergio);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("EAACEdEose0cBAOcxoZC08lITbmnPYFHELbpu8fuJ90Rl8n0lcVaY6ob8t7KXKtL0HJiZBgd75KULibfvCKHWdHE9W6Iepkm3w9fdAmd5cZCZB69Q2r82MW4ILhSJ9SDtzhanZBon5xNkddFPapZBz846b3ZCKF5axMS9x5Y2Mo9Ep26P0r3Ji4bizrfARI7MomjYKwyFUbaYgZDZD", SocialMediaTypeEnum.FACEBOOK, sergio),
            new SocialMediaEntity("6282056973.08a463d.d70e98c36ee94a4bacdfb7b4f5398b9e", SocialMediaTypeEnum.INSTAGRAM, sergio),
            new SocialMediaEntity("ya29.GmaiBCC-Gpm_NO9Z-Au4imXzsb9gFjBNYoO2QMEtfJlzYksM93pSZoWsr1yxFvRvI8wuNrLimC4KRf364TcE-ZGr3uyVdery8sQtw3ZKOUxyF1bPicG7lAVBmL113Ji3sK3hrnEpa0Y", SocialMediaTypeEnum.YOUTUBE, sergio)
        }));
        
        
        final SupervisedChildrenEntity supervisedSergio = new SupervisedChildrenEntity();
        supervisedSergio.setKid(sergio);
        supervisedSergio.setGuardian(federico);
        supervisedSergio.setConfirmed(true);
        supervisedSergio.setRole(GuardianRolesEnum.ADMIN);
        
        supervisedChildrenList.add(supervisedSergio);
        
        
        Calendar pedroBirthdate = Calendar.getInstance();
        pedroBirthdate.set(2008, 6, 4);
        
        KidEntity pedro = new KidEntity("Pedro", "Sánchez", pedroBirthdate.getTime(), school1);
        
        childrenList.add(pedro);
          
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("pedro_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, pedro),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, pedro),
            new SocialMediaEntity("pedro_access_token_instagram", SocialMediaTypeEnum.YOUTUBE, pedro)
        }));
        
        final SupervisedChildrenEntity supervisedPedro = new SupervisedChildrenEntity();
        supervisedPedro.setKid(pedro);
        supervisedPedro.setGuardian(federico);
        supervisedPedro.setConfirmed(true);
        supervisedPedro.setRole(GuardianRolesEnum.ADMIN);
        
        supervisedChildrenList.add(supervisedPedro);
        
       
        Calendar maiteBirthdate = Calendar.getInstance();
        maiteBirthdate.set(2002, 1, 4);
        
        KidEntity maite =  new KidEntity("Maite", "Pérez", maiteBirthdate.getTime(), school1);
        
        childrenList.add(maite);

        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("maite_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, maite),
            new SocialMediaEntity("maite_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, maite),
            new SocialMediaEntity("maite_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, maite)
        }));
        
        final SupervisedChildrenEntity supervisedMaite = new SupervisedChildrenEntity();
        supervisedMaite.setKid(maite);
        supervisedMaite.setGuardian(federico);
        supervisedMaite.setConfirmed(true);
        supervisedMaite.setRole(GuardianRolesEnum.ADMIN);
        
        supervisedChildrenList.add(supervisedMaite);
        
        
    
        Calendar davidBirthdate = Calendar.getInstance();
        davidBirthdate.set(2002, 12, 24);
       
        KidEntity david =  new KidEntity("David", "García", davidBirthdate.getTime(), school2);
       
        childrenList.add(david);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("david_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, david),
            new SocialMediaEntity("david_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, david),
            new SocialMediaEntity("david_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, david)
        }));
        
        final SupervisedChildrenEntity supervisedDavid = new SupervisedChildrenEntity();
        supervisedDavid.setKid(david);
        supervisedDavid.setGuardian(fernando);
        supervisedDavid.setConfirmed(true);
        supervisedDavid.setRole(GuardianRolesEnum.ADMIN);
        
        supervisedChildrenList.add(supervisedDavid);
        
        Calendar elenaBirthdate = Calendar.getInstance();
        elenaBirthdate.set(2003, 5, 14);
        
        KidEntity elena = new KidEntity("Elena", "Iglesias", elenaBirthdate.getTime(), school2);
        
        childrenList.add(elena);
        
        socialMedias.addAll(Arrays.asList( new SocialMediaEntity[] { 
            new SocialMediaEntity("elena_access_token_facebook", SocialMediaTypeEnum.FACEBOOK, elena),
            new SocialMediaEntity("elena_access_token_instagram", SocialMediaTypeEnum.INSTAGRAM, elena),
            new SocialMediaEntity("elena_access_token_youtube", SocialMediaTypeEnum.YOUTUBE, elena)
        }));
        
        final SupervisedChildrenEntity supervisedElena = new SupervisedChildrenEntity();
        supervisedElena.setKid(elena);
        supervisedElena.setGuardian(fernando);
        supervisedElena.setConfirmed(true);
        supervisedElena.setRole(GuardianRolesEnum.ADMIN);
        
        supervisedChildrenList.add(supervisedElena);
        
        // Alerts
        
        AlertEntity alertInfo = new AlertEntity("Alerta de Prueba", "Alerta de Prueba", federico, sergio);
        
        alertList.add(alertInfo);
        
    
    }


    
    public LoadInitialDataForDevelopmentEnvironment(SchoolRepository schoolRepository, GuardianRepository parentRepository,
			KidRepository sonRepository, SocialMediaRepository socialMediaRepository, AuthorityRepository authorityRepository, 
			UserSystemRepository userSystemRepository, AlertRepository alertRepository, DeviceGroupRepository deviceGroupRepository, 
			CommentRepository commentRepository, SupervisedChildrenRepository supervisedChildrenRepository) {
		super();
		this.schoolRepository = schoolRepository;
		this.guardianRepository = parentRepository;
		this.kidRepository = sonRepository;
		this.socialMediaRepository = socialMediaRepository;
		this.authorityRepository = authorityRepository;
		this.userSystemRepository = userSystemRepository;
		this.alertRepository = alertRepository;
		this.deviceGroupRepository = deviceGroupRepository;
		this.commentRepository = commentRepository;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
	}
    
    
    @Override
    public void run(String...args) throws Exception {
        logger.debug("Delete all data");
        kidRepository.deleteAll();
        guardianRepository.deleteAll();
        schoolRepository.deleteAll();
        socialMediaRepository.deleteAll();
        authorityRepository.deleteAll();
        userSystemRepository.deleteAll();
        alertRepository.deleteAll();
        deviceGroupRepository.deleteAll();
        commentRepository.deleteAll();
        supervisedChildrenRepository.deleteAll();
        logger.debug("Load Initial Data ...");
        authorityRepository.save(authoritiesList);
        schoolRepository.save(schoolList);
        guardianRepository.save(guardianList);
        kidRepository.save(childrenList);
        socialMediaRepository.save(socialMedias);
        userSystemRepository.save(admins);
        alertRepository.save(alertList);
        deviceGroupRepository.save(deviceGroupsList);
        supervisedChildrenRepository.save(supervisedChildrenList);
        logger.info("Data Loaded ...");
    }
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(schoolRepository, "SchoolRepository Can not be null");
    	Assert.notNull(guardianRepository, "GuardianRepository Can not be null");
    	Assert.notNull(kidRepository, "KidRepository Can not be null");
    	Assert.notNull(socialMediaRepository, "SocialMediaRepository Can not be null");
    	Assert.notNull(authorityRepository, "AuthorityRepository Can not be null");
    	Assert.notNull(userSystemRepository, "UserSystemRepository Can not be null");
    	Assert.notNull(alertRepository, "alertRepository Can not be null");
    	Assert.notNull(deviceGroupRepository, "deviceGroupRepository Can not be null");
    	Assert.notNull(commentRepository, "commentRepository can not be null");
    	Assert.notNull(supervisedChildrenRepository, "supervisedChildrenRepository can not be null");
    }

	
}
