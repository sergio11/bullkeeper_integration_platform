package es.bisite.usal.bulltect.domain.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.domain.service.IDeletePendingEmailService;
import es.bisite.usal.bulltect.persistence.entity.EmailTypeEnum;
import es.bisite.usal.bulltect.persistence.repository.EmailRepository;

@Service
public final class DeletePendingEmailServiceImpl implements IDeletePendingEmailService {
	
	private final EmailRepository emailRepository;
	
	public DeletePendingEmailServiceImpl(EmailRepository emailRepository) {
		super();
		this.emailRepository = emailRepository;
	}

	@Override
	public void deleteBySendToAndType(String sendTo, EmailTypeEnum type) {
		emailRepository.deleteBySendToAndType(sendTo, type);
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(emailRepository, "Email Repository can not be null");
	}

}
