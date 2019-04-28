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
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AuthorityEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AuthorityEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentAuthorEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
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
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppInstalledRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppStatsRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AuthorityRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CallDetailRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ContactEntityRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ConversationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.DeviceGroupRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.DevicePhotoRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.EmailRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GeofenceRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.IterationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRequestRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.MessageRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PasswordResetTokenRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PendingDeviceRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PhoneNumberBlockedRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.RememberMeTokenRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ScheduledBlockRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SchoolRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SmsRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SseEventRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TaskRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.UserSystemRepository;
import sanchez.sanchez.sergio.bullkeeper.util.Utils;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;

/**
 * @author sergio
 */

@Component
public class LoadInitialDataForAllEnvironment implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(LoadInitialDataForAllEnvironment.class);
    
    
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
    private final AppInstalledRepository appInstalledRepository;
    private final AppStatsRepository appStatsRepository;
    private final CallDetailRepository callDetailRepository;
    private final ContactEntityRepository contactEntityRepository;
    private final DevicePhotoRepository devicePhotoRepository;
    private final GeofenceRepository geofenceRepository;
    private final IterationRepository iterationRepository;
    private final KidRequestRepository kidRequestRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final PhoneNumberBlockedRepository phoneNumberBlockedRepository;
    private final ScheduledBlockRepository scheduledBlockRepository;
    private final SmsRepository smsRepository;
    private final SseEventRepository sseEventRepository;
    private final TaskRepository taskRepository;
    private final TerminalRepository terminalRepository;
    private final PendingDeviceRepository pendingDeviceRepository;
    private final EmailRepository emailRepository;
    private final RememberMeTokenRepository rememberMeTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final IUploadFilesService uploadFilesService;
    
    
    private static List<AuthorityEntity> authoritiesList = new ArrayList<>();
    private static List<SchoolEntity> schoolList = new ArrayList<>();
    private static List<GuardianEntity> guardianList = new ArrayList<>();
    private static List<KidEntity> childrenList = new ArrayList<>();
    private static List<SocialMediaEntity> socialMedias = new ArrayList<>();
    private static List<UserSystemEntity> admins = new ArrayList<>();
    private static List<AlertEntity> alertList = new ArrayList<>();
    private static List<DeviceGroupEntity> deviceGroupsList = new ArrayList<>();
    private static List<SupervisedChildrenEntity> supervisedChildrenList = new ArrayList<>();
    private static List<CommentEntity> commentsList = new ArrayList<>();
    
    
    static {
    	
    	// Authorities
    	AuthorityEntity adminRole = new AuthorityEntity(AuthorityEnum.ROLE_ADMIN, "Role for Administrators");
    	
    	authoritiesList.add(adminRole);
    	
    	AuthorityEntity guardianRole = new AuthorityEntity(AuthorityEnum.ROLE_GUARDIAN, "Role for Guardians");
    	
    	authoritiesList.add(guardianRole);
    	
    	AuthorityEntity anonymousRole = new AuthorityEntity(AuthorityEnum.ROLE_ANONYMOUS, "Role for Anonymous");
    	
    	authoritiesList.add(anonymousRole);
    	
    	
    	// SCHOOL
    	SchoolEntity school1 = new SchoolEntity("C.E.I.P. Fernando Gavilán", "Avda. Herrera Oria, s/n", 
    			"Cádiz", "+34958399591", "11008033.edu@juntadeandalucia.es");
    	
    	schoolList.add(school1);
    	
    	SchoolEntity school2 = new SchoolEntity("C.E.I.P. San Juan de Ávila", 
    			"C/ Erillas, s/n", "Granada", "+34958399591", "18005475.edu@juntadeandalucia.es");
    	
    	schoolList.add(school2);
    	
    	
    	// Guardians
    	
    	Calendar guardiansBirthdate = Calendar.getInstance();
    	guardiansBirthdate.set(1982, 4, 23);
    	
    
    	GuardianEntity federico = new GuardianEntity("Federico", "Martín", guardiansBirthdate.getTime(),
    			"federico@gmail.com", Utils.generateRandomUUID(), "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", 
    			guardianRole);
    	
    	federico.setVisible(true);
    	
    	guardianList.add(federico);
    	
    	GuardianEntity fernando = new GuardianEntity("Fernando", "Muñoz", guardiansBirthdate.getTime(),
    			"fernando@gmail.com", Utils.generateRandomUUID(), "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu",
    			guardianRole);
    	
    	fernando.setVisible(true);
    	
    	guardianList.add(fernando);
    	
    	GuardianEntity jaime = new GuardianEntity("Jaime", "Gómez", guardiansBirthdate.getTime(),
    			"jaime@gmail.com", Utils.generateRandomUUID(), "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", guardianRole);
    	
    	jaime.setVisible(true);
    	
    	guardianList.add(jaime);
    	
    	// ADMIN
    	
    	UserSystemEntity admin = new UserSystemEntity("Admin", "Admin", guardiansBirthdate.getTime(), 
    			"admin@gmail.com", Utils.generateRandomUUID(),  "$2a$10$0eCQpFRdw8i6jJzjj/IuNuKpJYnLaO5Yp9xSJ3itcfPmQNXVhmNyu", adminRole);
    	
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
        
        // Comments for facebook
        commentsList.addAll(getCommentsForFacebook(sergio));
        commentsList.addAll(getCommentsForInstagram(sergio));
        commentsList.addAll(getCommentsForYoutube(sergio));
        
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


    
    /**
     * 
     * @param schoolRepository
     * @param guardianRepository
     * @param kidRepository
     * @param socialMediaRepository
     * @param authorityRepository
     * @param userSystemRepository
     * @param alertRepository
     * @param deviceGroupRepository
     * @param commentRepository
     * @param supervisedChildrenRepository
     * @param appInstalledRepository
     * @param appStatsRepository
     * @param callDetailRepository
     * @param contactEntityRepository
     * @param devicePhotoRepository
     * @param geofenceRepository
     * @param iterationRepository
     * @param kidRequestRepository
     * @param conversationRepository
     * @param messageRepository
     * @param phoneNumberBlockedRepository
     * @param scheduledBlockRepository
     * @param smsRepository
     * @param sseEventRepository
     * @param taskRepository
     * @param terminalRepository
     * @param pendingDeviceRepository
     * @param emailRepository
     * @param rememberMeTokenRepository
     * @param passwordResetTokenRepository
     * @param uploadFilesService
     */

	public LoadInitialDataForAllEnvironment(SchoolRepository schoolRepository, GuardianRepository guardianRepository,
			KidRepository kidRepository, SocialMediaRepository socialMediaRepository,
			AuthorityRepository authorityRepository, UserSystemRepository userSystemRepository,
			AlertRepository alertRepository, DeviceGroupRepository deviceGroupRepository,
			CommentRepository commentRepository, SupervisedChildrenRepository supervisedChildrenRepository,
			AppInstalledRepository appInstalledRepository, AppStatsRepository appStatsRepository,
			CallDetailRepository callDetailRepository, ContactEntityRepository contactEntityRepository,
			DevicePhotoRepository devicePhotoRepository, GeofenceRepository geofenceRepository,
			IterationRepository iterationRepository, KidRequestRepository kidRequestRepository,
			ConversationRepository conversationRepository, MessageRepository messageRepository,
			PhoneNumberBlockedRepository phoneNumberBlockedRepository,
			ScheduledBlockRepository scheduledBlockRepository, SmsRepository smsRepository,
			SseEventRepository sseEventRepository, TaskRepository taskRepository, TerminalRepository terminalRepository,
			PendingDeviceRepository pendingDeviceRepository, EmailRepository emailRepository,
			RememberMeTokenRepository rememberMeTokenRepository,
			PasswordResetTokenRepository passwordResetTokenRepository,
			IUploadFilesService uploadFilesService) {
		super();
		this.schoolRepository = schoolRepository;
		this.guardianRepository = guardianRepository;
		this.kidRepository = kidRepository;
		this.socialMediaRepository = socialMediaRepository;
		this.authorityRepository = authorityRepository;
		this.userSystemRepository = userSystemRepository;
		this.alertRepository = alertRepository;
		this.deviceGroupRepository = deviceGroupRepository;
		this.commentRepository = commentRepository;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
		this.appInstalledRepository = appInstalledRepository;
		this.appStatsRepository = appStatsRepository;
		this.callDetailRepository = callDetailRepository;
		this.contactEntityRepository = contactEntityRepository;
		this.devicePhotoRepository = devicePhotoRepository;
		this.geofenceRepository = geofenceRepository;
		this.iterationRepository = iterationRepository;
		this.kidRequestRepository = kidRequestRepository;
		this.conversationRepository = conversationRepository;
		this.messageRepository = messageRepository;
		this.phoneNumberBlockedRepository = phoneNumberBlockedRepository;
		this.scheduledBlockRepository = scheduledBlockRepository;
		this.smsRepository = smsRepository;
		this.sseEventRepository = sseEventRepository;
		this.taskRepository = taskRepository;
		this.terminalRepository = terminalRepository;
		this.pendingDeviceRepository = pendingDeviceRepository;
		this.emailRepository = emailRepository;
		this.rememberMeTokenRepository = rememberMeTokenRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.uploadFilesService = uploadFilesService;
	}

	private static List<CommentEntity> getCommentsForFacebook(final KidEntity kid){
    	
    	final List<CommentEntity> commentsList = new ArrayList<>();
    	
    	final CommentAuthorEntity author = new CommentAuthorEntity();
    	author.setName("Sergio Martín");
    	author.setImage("https://scontent.fmad3-6.fna.fbcdn.net/v/t1.0-1/c27.0.160.160a/p160x160/25680_102397813134846_2757118_n.jpg?_nc_cat=109&_nc_ht=scontent.fmad3-6.fna&oh=031a58b226f0e426ddd0809e05deb0e2&oe=5CBDBD5B");
    	author.setExternalId("1234243");
    	
    	final CommentEntity comment1 = new CommentEntity();
        comment1.setCreatedTime(new Date());
        comment1.setExtractedAt(new Date());
        comment1.setKid(kid);
        comment1.setLikes(12L);
        comment1.setAuthor(author);
        comment1.setMessage("No me lo puedo creer ..., de verdad piensas eso?? WTF..");
        comment1.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment1);
        
        
        final CommentEntity comment2 = new CommentEntity();
        comment2.setCreatedTime(new Date());
        comment2.setExtractedAt(new Date());
        comment2.setKid(kid);
        comment2.setLikes(12L);
        comment2.setAuthor(author);
        comment2.setMessage("Muchas gracias de verdad :)");
        comment2.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment2);
        
        final CommentEntity comment3 = new CommentEntity();
        comment3.setCreatedTime(new Date());
        comment3.setExtractedAt(new Date());
        comment3.setKid(kid);
        comment3.setLikes(12L);
        comment3.setAuthor(author);
        comment3.setMessage("que bonito recuerdo de cuando creía que me quedaban pocas horas de vidaa jajajja 💜💜");
        comment3.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment3);
        
        final CommentEntity comment4 = new CommentEntity();
        comment4.setCreatedTime(new Date());
        comment4.setExtractedAt(new Date());
        comment4.setKid(kid);
        comment4.setLikes(12L);
        comment4.setAuthor(author);
        comment4.setMessage("Qué chula la foto. Muchos besos!");
        comment4.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment4);
        
        
        final CommentEntity comment5 = new CommentEntity();
        comment5.setCreatedTime(new Date());
        comment5.setExtractedAt(new Date());
        comment5.setKid(kid);
        comment5.setLikes(12L);
        comment5.setAuthor(author);
        comment5.setMessage("Gracias... xDD (Que seco lo de los 3 puntos, no) ;) )");
        comment5.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment5);
        
        
        final CommentEntity comment6 = new CommentEntity();
        comment6.setCreatedTime(new Date());
        comment6.setExtractedAt(new Date());
        comment6.setKid(kid);
        comment6.setLikes(12L);
        comment6.setAuthor(author);
        comment6.setMessage("Pero que ladras");
        comment6.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment6);
        
        final CommentEntity comment7 = new CommentEntity();
        comment7.setCreatedTime(new Date());
        comment7.setExtractedAt(new Date());
        comment7.setKid(kid);
        comment7.setLikes(1L);
        comment7.setAuthor(author);
        comment7.setMessage("Oye... de verdad, más gilipollas no se puede nacer");
        comment7.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment7);
        
        final CommentEntity comment8 = new CommentEntity();
        comment8.setCreatedTime(new Date());
        comment8.setExtractedAt(new Date());
        comment8.setKid(kid);
        comment8.setLikes(0L);
        comment8.setAuthor(author);
        comment8.setMessage("Que te den por el puto culo macho, más tonto no se puede ser");
        comment8.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment8);
        
        final CommentEntity comment9 = new CommentEntity();
        comment9.setCreatedTime(new Date());
        comment9.setExtractedAt(new Date());
        comment9.setKid(kid);
        comment9.setLikes(2L);
        comment9.setAuthor(author);
        comment9.setMessage("Tú sigue por ahí subnormal, que te van llover ostias");
        comment9.setSocialMedia(SocialMediaTypeEnum.FACEBOOK);
        commentsList.add(comment9);
    	
    	return commentsList;
    }
    
	private static List<CommentEntity> getCommentsForInstagram(final KidEntity kid){
	    	
	    	final List<CommentEntity> commentsList = new ArrayList<>();
	    	
	    	final CommentAuthorEntity author = new CommentAuthorEntity();
	    	author.setName("Sergio Martín");
	    	author.setImage("https://instagram.fmad3-7.fna.fbcdn.net/vp/c1adf612ddd178306d30daf29dc9fb3c/5CC96388/t51.2885-19/s150x150/44456119_184243215847397_3702603430850723840_n.jpg?_nc_ht=instagram.fmad3-7.fna.fbcdn.net");
	    	author.setExternalId("1234243");
	    	
	    	final CommentEntity comment1 = new CommentEntity();
	        comment1.setCreatedTime(new Date());
	        comment1.setExtractedAt(new Date());
	        comment1.setKid(kid);
	        comment1.setLikes(2l);
	        comment1.setAuthor(author);
	        comment1.setMessage("Menuda foto XD, Cómo subes eso?? jajajaj. Yo si fuera tú me pegaba un tiro");
	        comment1.setSocialMedia(SocialMediaTypeEnum.INSTAGRAM);
	        commentsList.add(comment1);
	        
	        
	        final CommentEntity comment2 = new CommentEntity();
	        comment2.setCreatedTime(new Date());
	        comment2.setExtractedAt(new Date());
	        comment2.setKid(kid);
	        comment2.setLikes(0l);
	        comment2.setAuthor(author);
	        comment2.setMessage("Genial!!, fotos como estas me dan ganas de sucidarme");
	        comment2.setSocialMedia(SocialMediaTypeEnum.INSTAGRAM);
	        commentsList.add(comment2);
	        
	        final CommentEntity comment3 = new CommentEntity();
	        comment3.setCreatedTime(new Date());
	        comment3.setExtractedAt(new Date());
	        comment3.setKid(kid);
	        comment3.setLikes(3l);
	        comment3.setAuthor(author);
	        comment3.setMessage("Muy bonito todo.... si señor, te felicito");
	        comment3.setSocialMedia(SocialMediaTypeEnum.INSTAGRAM);
	        commentsList.add(comment3);
	        
	        final CommentEntity comment4 = new CommentEntity();
	        comment4.setCreatedTime(new Date());
	        comment4.setExtractedAt(new Date());
	        comment4.setKid(kid);
	        comment4.setLikes(5l);
	        comment4.setAuthor(author);
	        comment4.setMessage("No se puede ser más tonto de las narices");
	        comment4.setSocialMedia(SocialMediaTypeEnum.INSTAGRAM);
	        commentsList.add(comment4);
	        
	        
	        final CommentEntity comment5 = new CommentEntity();
	        comment5.setCreatedTime(new Date());
	        comment5.setExtractedAt(new Date());
	        comment5.setKid(kid);
	        comment5.setLikes(6l);
	        comment5.setAuthor(author);
	        comment5.setMessage("Que feo eres macho, orco!!!");
	        comment5.setSocialMedia(SocialMediaTypeEnum.INSTAGRAM);
	        commentsList.add(comment5);
	        
	        
	        final CommentEntity comment6 = new CommentEntity();
	        comment6.setCreatedTime(new Date());
	        comment6.setExtractedAt(new Date());
	        comment6.setKid(kid);
	        comment6.setLikes(12l);
	        comment6.setAuthor(author);
	        comment6.setMessage("Muy profundo si....");
	        comment6.setSocialMedia(SocialMediaTypeEnum.INSTAGRAM);
	        commentsList.add(comment6);
	        
	        final CommentEntity comment7 = new CommentEntity();
	        comment7.setCreatedTime(new Date());
	        comment7.setExtractedAt(new Date());
	        comment7.setKid(kid);
	        comment7.setLikes(24l);
	        comment7.setAuthor(author);
	        comment7.setMessage("Fúmate todo eso tio jajaja");
	        comment7.setSocialMedia(SocialMediaTypeEnum.INSTAGRAM);
	        commentsList.add(comment7);
	    	
	    	return commentsList;
	}




	private static List<CommentEntity> getCommentsForYoutube(final KidEntity kid){
		
		final List<CommentEntity> commentsList = new ArrayList<>();
		
		final CommentAuthorEntity author = new CommentAuthorEntity();
		author.setName("Sergio Martín");
		author.setImage("https://scontent.fmad3-6.fna.fbcdn.net/v/t1.0-1/c27.0.160.160a/p160x160/25680_102397813134846_2757118_n.jpg?_nc_cat=109&_nc_ht=scontent.fmad3-6.fna&oh=031a58b226f0e426ddd0809e05deb0e2&oe=5CBDBD5B");
		author.setExternalId("1234243");
		
		final CommentEntity comment1 = new CommentEntity();
	    comment1.setCreatedTime(new Date());
	    comment1.setExtractedAt(new Date());
	    comment1.setKid(kid);
	    comment1.setLikes(0l);
	    comment1.setAuthor(author);
	    comment1.setMessage("Una puta mierda de video, en serio, dejar de subir mierda así...");
	    comment1.setSocialMedia(SocialMediaTypeEnum.YOUTUBE);
	    commentsList.add(comment1);
	    
	    
	    final CommentEntity comment2 = new CommentEntity();
	    comment2.setCreatedTime(new Date());
	    comment2.setExtractedAt(new Date());
	    comment2.setKid(kid);
	    comment2.setLikes(0l);
	    comment2.setAuthor(author);
	    comment2.setMessage("En cada vídeo te superas macho, no se puede ser más gilipollas");
	    comment2.setSocialMedia(SocialMediaTypeEnum.YOUTUBE);
	    commentsList.add(comment2);
	    
	    final CommentEntity comment3 = new CommentEntity();
	    comment3.setCreatedTime(new Date());
	    comment3.setExtractedAt(new Date());
	    comment3.setKid(kid);
	    comment3.setLikes(2l);
	    comment3.setAuthor(author);
	    comment3.setMessage("Te voy a dar un like por pena hombre jajaja");
	    comment3.setSocialMedia(SocialMediaTypeEnum.YOUTUBE);
	    commentsList.add(comment3);
	   
		
		return commentsList;
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
        appInstalledRepository.deleteAll();
        appStatsRepository.deleteAll();
        callDetailRepository.deleteAll();
        contactEntityRepository.deleteAll();
        devicePhotoRepository.deleteAll();
        geofenceRepository.deleteAll();
        iterationRepository.deleteAll();
        kidRequestRepository.deleteAll();
        conversationRepository.deleteAll();
        messageRepository.deleteAll();
        phoneNumberBlockedRepository.deleteAll();
        scheduledBlockRepository.deleteAll();
        smsRepository.deleteAll();
        sseEventRepository.deleteAll();
        taskRepository.deleteAll();
        terminalRepository.deleteAll();
        pendingDeviceRepository.deleteAll();
        emailRepository.deleteAll();
        rememberMeTokenRepository.deleteAll();
        passwordResetTokenRepository.deleteAll();
        uploadFilesService.deleteAll();
        
        
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
        commentRepository.save(commentsList);
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
