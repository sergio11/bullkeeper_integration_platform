package sanchez.sanchez.sergio.bullkeeper.events.handlers;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.events.apps.AppDisabledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppEnabledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppRulesListSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppRulesSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.apps.AppDisabledStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.apps.AppRulesListSavedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.apps.AppRulesSavedSSE;
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
	 * 
	 * @param sseService
	 */
	public AppsRulesEventHandlers(
			final ISseService sseService) {
		super();
		this.sseService = sseService;
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
							appDisabledEvent.getTerminal(), appDisabledEvent.getApp(), false);
		
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
						appEnabledEvent.getTerminal(), appEnabledEvent.getApp(), true);
	
		// Push Event
		sseService.push(subscriberId, appDisabledStatusChanged);
	}
	
	
	
}
