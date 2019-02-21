package sanchez.sanchez.sergio.bullkeeper.events.apps.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppDisabledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppEnabledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppRulesListSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppRulesSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.NewAppInstalledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.UninstallAppEvent;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.apps.AppDisabledStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.apps.AppRulesListSavedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.apps.AppRulesSavedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.apps.NewAppInstalledSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.apps.UninstallAppSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppRulesDTO;


/**
 * Apps Rules Event Handlers
 */
@Component
public class AppsRulesEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(AppsRulesEventHandlers.class);
	  
	/**
	 * SSE Service
	 */
	private final ISseService sseService;
	
	/**
	 * Supervised Children Repository
	 */
	private final SupervisedChildrenRepository supervisedChildrenRepository;
	

	/**
	 * 
	 * @param sseService
	 */
	public AppsRulesEventHandlers(
			final ISseService sseService,
			final SupervisedChildrenRepository supervisedChildrenRepository) {
		super();
		this.sseService = sseService;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
	}

	/**
	 * Handle for App Rules List Event 
	 * @param appRulesListSavedEvent
	 */
	@EventListener
	public void handle(final AppRulesListSavedEvent appRulesListSavedEvent) {
		Assert.notNull(appRulesListSavedEvent, "App Rules List Saved Event can not be null");
		
		
		final List<AppRulesListSavedSSE.AppRules> appRules = 
				new ArrayList<>();
		
		for(final SaveAppRulesDTO appRulesDTO: appRulesListSavedEvent.getSaveAppRulesDTO()) {
			appRules.add(new AppRulesListSavedSSE.AppRules(
					appRulesDTO.getIdentity(), appRulesDTO.getType()));
			
		} 
		
		final String subscriberId = appRulesListSavedEvent.getTerminal();
		
		final AppRulesListSavedSSE appRulesSse = 
				new AppRulesListSavedSSE(
						subscriberId, appRulesListSavedEvent.getKid(),
						appRulesListSavedEvent.getTerminal(), appRules);
		
		// Push Event
		sseService.push(subscriberId, appRulesSse);
		
	}
	
	
	/**
	 * Handle App Rules Saved Event
	 * @param appRulesSavedEvent
	 */
	@EventListener
	public void handle(final AppRulesSavedEvent appRulesSavedEvent) {
		Assert.notNull(appRulesSavedEvent, "App Rules Saved Event can not be null");
		
		final String subscriberId = appRulesSavedEvent.getTerminal();
		
		// Map To App Rules Saved SSE
		final AppRulesSavedSSE appRulesSavedSSE = 
				new AppRulesSavedSSE(subscriberId, appRulesSavedEvent.getKid(),
						appRulesSavedEvent.getTerminal(),
						appRulesSavedEvent.getApp(), appRulesSavedEvent.getAppRule());
		
		
		// Push Event
		sseService.push(subscriberId, appRulesSavedSSE);
	}
	
	/**
	 * Handle App Disabled Event
	 * @param appDisabledEvent
	 */
	@EventListener
	public void handle(final AppDisabledEvent appDisabledEvent) {
		Assert.notNull(appDisabledEvent, "Event can not be null");
		
		final String subscriberId = appDisabledEvent.getTerminal();
		
		final AppDisabledStatusChangedSSE appDisabledStatusChanged  = 
					new AppDisabledStatusChangedSSE(subscriberId,
							appDisabledEvent.getKid(),
							appDisabledEvent.getTerminal(), appDisabledEvent.getApp(), true);
		
		// Push Event
		sseService.push(subscriberId, appDisabledStatusChanged);
	}
	
	
	/**
	 * Handle App Enabled Event
	 * @param appEnabledEvent
	 */
	@EventListener
	public void handle(final AppEnabledEvent appEnabledEvent) {
		Assert.notNull(appEnabledEvent, "Event can not be null");
		final String subscriberId = appEnabledEvent.getTerminal();
		
		final AppDisabledStatusChangedSSE appDisabledStatusChanged  = 
				new AppDisabledStatusChangedSSE(subscriberId,
						appEnabledEvent.getKid(),
						appEnabledEvent.getTerminal(), appEnabledEvent.getApp(), false);
	
		// Push Event
		sseService.push(subscriberId, appDisabledStatusChanged);
	}
	
	/**
	 * Handle Uninstall App Event
	 * @param uninstallAppEvent
	 */
	@EventListener
	public void handle(final UninstallAppEvent uninstallAppEvent) {
		Assert.notNull(uninstallAppEvent, "Uninstall App Event can not be null");
		
		final List<SupervisedChildrenEntity> supervisedChildren = supervisedChildrenRepository.findByKidIdAndRoleInAndIsConfirmedTrue(
				new ObjectId(uninstallAppEvent.getKid()), 
				Arrays.asList(GuardianRolesEnum.PARENTAL_CONTROL_RULE_EDITOR,
						GuardianRolesEnum.ADMIN));
	
		for(final SupervisedChildrenEntity supervisedChildrenEntity: supervisedChildren) {
			
			final String subscriberId = supervisedChildrenEntity.getGuardian().getId().toString();
			
			final UninstallAppSSE uninstallAppSSE = new UninstallAppSSE();
			uninstallAppSSE.setIdentity(uninstallAppEvent.getApp().getIdentity());
			uninstallAppSSE.setAppName(uninstallAppEvent.getApp().getAppName());
			uninstallAppSSE.setAppRule(uninstallAppEvent.getApp().getAppRule());
			uninstallAppSSE.setDisabled(uninstallAppEvent.getApp().getDisabled());
			uninstallAppSSE.setFirstInstallTime(uninstallAppEvent.getApp().getFirstInstallTime());
			uninstallAppSSE.setLastUpdateTime(uninstallAppEvent.getApp().getLastUpdateTime());
			uninstallAppSSE.setIconEncodedString(uninstallAppEvent.getApp().getIconEncodedString());
			uninstallAppSSE.setVersionCode(uninstallAppEvent.getApp().getVersionCode());
			uninstallAppSSE.setVersionName(uninstallAppEvent.getApp().getVersionName());
			uninstallAppSSE.setPackageName(uninstallAppEvent.getApp().getPackageName());
			uninstallAppSSE.setKid(uninstallAppEvent.getApp().getKid());
			uninstallAppSSE.setTerminalId(uninstallAppEvent.getApp().getTerminalId());
			uninstallAppSSE.setSubscriberId(subscriberId);
			
			// Push Event
			sseService.push(subscriberId, uninstallAppSSE);
		}
	
		
	}
	
	/**
	 * Handle New App Installed Event
	 * @param newAppInstalledEvent
	 */
	@EventListener
	public void handle(final NewAppInstalledEvent newAppInstalledEvent) {
		Assert.notNull(newAppInstalledEvent, "New App Installed Event");
		
		final List<SupervisedChildrenEntity> supervisedChildren = supervisedChildrenRepository.findByKidIdAndRoleInAndIsConfirmedTrue(
				new ObjectId(newAppInstalledEvent.getKid()), 
				Arrays.asList(GuardianRolesEnum.PARENTAL_CONTROL_RULE_EDITOR,
						GuardianRolesEnum.ADMIN));
	
		for(final SupervisedChildrenEntity supervisedChildrenEntity: supervisedChildren) {
			
			final String subscriberId = supervisedChildrenEntity.getGuardian().getId().toString();
			
			final NewAppInstalledSSE newAppInstalledSSE = new NewAppInstalledSSE();
			newAppInstalledSSE.setIdentity(newAppInstalledEvent.getApp().getIdentity());
			newAppInstalledSSE.setAppName(newAppInstalledEvent.getApp().getAppName());
			newAppInstalledSSE.setAppRule(newAppInstalledEvent.getApp().getAppRule());
			newAppInstalledSSE.setDisabled(newAppInstalledEvent.getApp().getDisabled());
			newAppInstalledSSE.setFirstInstallTime(newAppInstalledEvent.getApp().getFirstInstallTime());
			newAppInstalledSSE.setLastUpdateTime(newAppInstalledEvent.getApp().getLastUpdateTime());
			newAppInstalledSSE.setIconEncodedString(newAppInstalledEvent.getApp().getIconEncodedString());
			newAppInstalledSSE.setVersionCode(newAppInstalledEvent.getApp().getVersionCode());
			newAppInstalledSSE.setVersionName(newAppInstalledEvent.getApp().getVersionName());
			newAppInstalledSSE.setPackageName(newAppInstalledEvent.getApp().getPackageName());
			newAppInstalledSSE.setKid(newAppInstalledEvent.getApp().getKid());
			newAppInstalledSSE.setTerminalId(newAppInstalledEvent.getApp().getTerminalId());
			newAppInstalledSSE.setSubscriberId(subscriberId);
			
			// Push Event
			sseService.push(subscriberId, newAppInstalledSSE);
		}
	}
}
