package es.bisite.usal.bulltect.domain.service;

import es.bisite.usal.bulltect.persistence.entity.EmailTypeEnum;

/**
 *
 * @author sergio
 */
public interface IDeletePendingEmailService {
	void deleteBySendToAndType(String sendTo, EmailTypeEnum type);
}
