package sanchez.sanchez.sergio.bullkeeper.events.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.DeleteAllScheduledBlockEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.DeleteScheduledBlockEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.ScheduledBlockImageChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.ScheduledBlockSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.ScheduledBlockStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.mapper.ScheduledBlockMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks.DeleteAllScheduledBlockSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks.DeleteScheduledBlockSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks.ScheduledBlockImageChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks.ScheduledBlockSavedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks.ScheduledBlockStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockStatusDTO;

/**
 * Scheduled Block Event Handlers
 */
@Component
public class ScheduledBlockEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(ScheduledBlockEventHandlers.class);
	    
	/**
	 * Scheduled Block Mapper
	 */
	private final ScheduledBlockMapper scheduledBlockMapper;
	
	/**
	 * SSE Service
	 */
	private final ISseService sseService;
	
	/**
	 * Terminal Repository
	 */
	private final TerminalRepository terminalRepository;
	
	
	/**
	 * 
	 * @param scheduledBlockMapper
	 * @param sseService
	 * @param terminalRepository
	 */
	@Autowired
	public ScheduledBlockEventHandlers(
			final ScheduledBlockMapper scheduledBlockMapper,
			final ISseService sseService,
			final TerminalRepository terminalRepository) {
		super();
		this.scheduledBlockMapper = scheduledBlockMapper;
		this.sseService = sseService;
		this.terminalRepository = terminalRepository;
	}
	
	/**
	 * Send To All Terminals
	 * @param kid
	 * @param data
	 */
	private <T extends AbstractSseData> void sendToAllTerminals(final String kid, final T data) {
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminals = terminalRepository.findByKidId(
					new ObjectId(kid));
								
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminals) {
			data.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), data);
		}
	}

	/**
	 * Handle Scheduled Block Saved Event
	 * @param scheduledBlockSavedEvent
	 */
	@EventListener
	public void handle(final ScheduledBlockSavedEvent scheduledBlockSavedEvent) {
		Assert.notNull(scheduledBlockSavedEvent, "Scheduled Block Saved Event can not be null");
	
		final ScheduledBlockSavedSSE scheduledBlockSavedSSE = 
				scheduledBlockMapper.scheduledBlockDTOToScheduledBlockSavedSSE(scheduledBlockSavedEvent.getScheduledBlockDTO());
		
		sendToAllTerminals(scheduledBlockSavedEvent.getKid(),
				scheduledBlockSavedSSE);
	}
	
	
	/**
	 * Handle Delete Scheduled Block Event
	 * @param deleteScheduledBlockEvent
	 */
	@EventListener
	public void handle(final DeleteScheduledBlockEvent deleteScheduledBlockEvent) {
		Assert.notNull(deleteScheduledBlockEvent, "Delete Scheduled Block Removed Event can not be null");
		
		final DeleteScheduledBlockSSE deleteScheduledBlockSSE = 
				new DeleteScheduledBlockSSE();
		deleteScheduledBlockSSE.setKid(deleteScheduledBlockEvent.getKid());
		deleteScheduledBlockSSE.setBlock(deleteScheduledBlockEvent.getBlock());
		
		sendToAllTerminals(deleteScheduledBlockEvent.getKid(),
				deleteScheduledBlockSSE);
	}
	
	
	/**
	 * Handle Delete All Scheduled Block Event
	 * @param deleteAllScheduledBlockEvent
	 */
	@EventListener
	public void handle(final DeleteAllScheduledBlockEvent deleteAllScheduledBlockEvent) {
		Assert.notNull(deleteAllScheduledBlockEvent, "Delete All Scheduled Block can not be null");
		
		final DeleteAllScheduledBlockSSE deleteAllScheduledBlockSSE 
			= new DeleteAllScheduledBlockSSE();
		deleteAllScheduledBlockSSE.setKid(deleteAllScheduledBlockEvent.getKid());
		
		sendToAllTerminals(deleteAllScheduledBlockEvent.getKid(),
				deleteAllScheduledBlockSSE);
	}
	
	
	/**
	 * Handle Scheduled Block Image Changed Event
	 * @param scheduledBlockImageChangedEvent
	 */
	@EventListener
	public void handle(final ScheduledBlockImageChangedEvent scheduledBlockImageChangedEvent) {
		Assert.notNull(scheduledBlockImageChangedEvent, "Scheduled Block Image Changed Event can not be null");
	
		final ScheduledBlockImageChangedSSE scheduledBlockImageChangedSSE = 
				new ScheduledBlockImageChangedSSE();
		scheduledBlockImageChangedSSE.setKid(scheduledBlockImageChangedEvent.getKid());
		scheduledBlockImageChangedSSE.setBlock(scheduledBlockImageChangedEvent.getBlock());
		scheduledBlockImageChangedSSE.setImage(scheduledBlockImageChangedEvent.getImage());
		
		sendToAllTerminals(scheduledBlockImageChangedEvent.getKid(),
				scheduledBlockImageChangedSSE);
		
		
	}
	
	
	/**
	 * Handle Scheduled Block Status Changed Event
	 * @param scheduledBlockStatusChangedEvent
	 */
	@EventListener
	public void handle(final ScheduledBlockStatusChangedEvent scheduledBlockStatusChangedEvent) {
		Assert.notNull(scheduledBlockStatusChangedEvent, "Scheduled Block Status Changed Event");
	
		final List<ScheduledBlockStatusChangedSSE.ScheduledBlockStatus> 
				scheduledBlockStatus = new ArrayList<>();
		
		for(final SaveScheduledBlockStatusDTO scheduledBlockDTO: scheduledBlockStatusChangedEvent.getScheduledBlockStatusList()) {
			scheduledBlockStatus.add(new ScheduledBlockStatusChangedSSE.ScheduledBlockStatus(scheduledBlockDTO.getIdentity(), 
					scheduledBlockDTO.isEnable()));
		}

		final ScheduledBlockStatusChangedSSE scheduledBlockStatusChangedSSE = 
				new ScheduledBlockStatusChangedSSE();
		scheduledBlockStatusChangedSSE.setKid(scheduledBlockStatusChangedEvent.getKid());
		scheduledBlockStatusChangedSSE.setScheduledBlockStatusList(scheduledBlockStatus);
	
		sendToAllTerminals(scheduledBlockStatusChangedEvent.getKid(),
				scheduledBlockStatusChangedSSE);
		
	}
}
