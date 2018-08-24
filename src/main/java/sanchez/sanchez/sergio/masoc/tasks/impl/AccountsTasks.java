package sanchez.sanchez.sergio.masoc.tasks.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.masoc.domain.service.IParentsService;
import sanchez.sanchez.sergio.masoc.domain.service.IPasswordResetTokenService;
import sanchez.sanchez.sergio.masoc.helper.IDeviceHelper;
import sanchez.sanchez.sergio.masoc.mail.service.IMailClientService;
import sanchez.sanchez.sergio.masoc.persistence.entity.EmailEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.PendingDeviceEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.EmailRepository;
import sanchez.sanchez.sergio.masoc.persistence.repository.PendingDeviceRepository;

@Component
public class AccountsTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountsTasks.class);
	
	private final IPasswordResetTokenService passwordResetTokenService;
	private final IParentsService parentService;
	private final EmailRepository emailRepository;
	private final IMailClientService mailClientService;
	private final PendingDeviceRepository pendingDeviceRepository;
	private final IDeviceHelper deviceHelper;
	
	@Value("${task.account.unsuccessful.mail.forwarding.number.of.emails.to.forward}")
    private Integer numberOfEmailsToForwarding;
	
	@Autowired
	public AccountsTasks(IPasswordResetTokenService passwordResetTokenService, 
			IParentsService parentService, EmailRepository emailRepository, IMailClientService mailClientService,
			PendingDeviceRepository pendingDeviceRepository, IDeviceHelper deviceHelper){
		this.passwordResetTokenService = passwordResetTokenService;
		this.parentService = parentService;
		this.emailRepository = emailRepository;
		this.mailClientService = mailClientService;
		this.pendingDeviceRepository = pendingDeviceRepository;
		this.deviceHelper = deviceHelper;
	}
	
	
	@Scheduled(cron = "${task.account.delete.expired.tokens}")
    public void deleteExpiredPasswordTokens() {
        logger.debug("Delete Expired Tokens ...");
        passwordResetTokenService.deleteExpiredTokens();
    }

    @Scheduled(cron = "${task.account.delete.unactivated.accounts}")
    public void deleteUnactivatedAccounts() {
        logger.debug("Delete Unactivated Accounts ...");
        Long total = parentService.deleteUnactivatedAccounts();
        logger.debug(total + " inactive accounts deleted");
    }

    @Scheduled(cron = "${task.account.cancel.account.deletion.proccess}")
    public void cancelAccountDeletionProcess() {
        logger.debug("Cancel Account Deletion Proccess ...");
        parentService.cancelAllAccountDeletionProcess();
    }
    
    @Scheduled(cron = "${task.account.unsuccessful.mail.forwarding}")
    public void unsuccessfulMailForwarding() {
        logger.debug("Unsuccessful Mail Forwarding ...");
        List<EmailEntity> emailsToForward = emailRepository.findAllByOrderByLastChanceAsc(new PageRequest(0, numberOfEmailsToForwarding));
        for(EmailEntity emailToForward: emailsToForward)
            mailClientService.sendEmail(emailToForward.getSendTo(), emailToForward.getType(),
                    emailToForward.getSubject(), emailToForward.getContent());
    }
    
    @Scheduled(cron = "${task.account.register.pending.devices}")
    public void registerPendingDevices() {
    	 logger.debug("Register Pending Devices ...");
    	 final List<PendingDeviceEntity> pendingDevices = pendingDeviceRepository.findAll();
    	 for(PendingDeviceEntity pendingDevice: pendingDevices)
    		 deviceHelper.createOrUpdateDevice(pendingDevice.getOwner(), pendingDevice.getDeviceId(), pendingDevice.getRegistrationToken());
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(passwordResetTokenService, "Password Reset Token Service can not be null");
        Assert.notNull(parentService, "Parent Service can not be null");
        Assert.notNull(emailRepository, "Email Repository can not be null");
        Assert.notNull(mailClientService, "Mail Client Service can not be null");
        Assert.notNull(pendingDeviceRepository, "PendingDeviceRepository can not be null");
        Assert.notNull(deviceHelper, "DeviceHelper can not be null");
        
        logger.debug("init Account Tasks ...");
    }

}
