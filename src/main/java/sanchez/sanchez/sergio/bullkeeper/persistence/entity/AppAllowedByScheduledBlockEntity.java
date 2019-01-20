package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * App Allowed By Scheduled Block Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class AppAllowedByScheduledBlockEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * APp
	 */
	@DBRef
	@Field("app")
	private AppInstalledEntity app; 
	
	/**
	 * Terminal
	 */
	@DBRef
	@Field("terminal")
	protected TerminalEntity terminal;
	
	/**
	 * 
	 */
	public AppAllowedByScheduledBlockEntity() {}

	/**
	 
	 * @param app
	 * @param terminal
	 */
	public AppAllowedByScheduledBlockEntity(final AppInstalledEntity app, 
			final TerminalEntity terminal) {
		super();
		this.app = app;
		this.terminal = terminal;
	}

	public AppInstalledEntity getApp() {
		return app;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setApp(AppInstalledEntity app) {
		this.app = app;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "AppAllowedByScheduledBlockEntity [app=" + app + ", terminal=" + terminal + "]";
	}
}
