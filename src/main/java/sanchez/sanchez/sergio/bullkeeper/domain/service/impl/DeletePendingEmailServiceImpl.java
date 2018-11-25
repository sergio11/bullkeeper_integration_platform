package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IDeletePendingEmailService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.EmailTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.EmailRepository;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Service
public final class DeletePendingEmailServiceImpl implements IDeletePendingEmailService {
	
	private final EmailRepository emailRepository;
	
	/**
	 * 
	 * @param emailRepository
	 */
	public DeletePendingEmailServiceImpl(EmailRepository emailRepository) {
		super();
		this.emailRepository = emailRepository;
	}

	/**
	 * 
	 */
	@Override
	public void deleteBySendToAndType(String sendTo, EmailTypeEnum type) {
		emailRepository.deleteBySendToAndType(sendTo, type);
	}
	
	/**
	 * 
	 */
	@PostConstruct
	protected void init(){
		Assert.notNull(emailRepository, "Email Repository can not be null");
	}

}
