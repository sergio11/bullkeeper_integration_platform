package sanchez.sanchez.sergio.service.impl;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.dto.response.PasswordResetTokenDTO;
import sanchez.sanchez.sergio.mapper.IPasswordResetTokenEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.PasswordResetTokenEntity;
import sanchez.sanchez.sergio.persistence.repository.PasswordResetTokenRepository;
import sanchez.sanchez.sergio.service.IPasswordResetTokenService;
import sanchez.sanchez.sergio.service.ITokenGeneratorService;

@Service
public final class PasswordResetTokenServiceImpl implements IPasswordResetTokenService {
	
	private static Logger logger = LoggerFactory.getLogger(PasswordResetTokenServiceImpl.class);
	
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final ITokenGeneratorService tokenGeneratorService;
	private final IPasswordResetTokenEntityMapper passwordResetTokenEntityMapper;
	
	@Autowired
	public PasswordResetTokenServiceImpl(PasswordResetTokenRepository passwordResetTokenRepository,
			ITokenGeneratorService tokenGeneratorService, IPasswordResetTokenEntityMapper passwordResetTokenEntityMapper) {
		super();
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.tokenGeneratorService = tokenGeneratorService;
		this.passwordResetTokenEntityMapper = passwordResetTokenEntityMapper;
	}


	@Override
	public PasswordResetTokenDTO createPasswordResetTokenForUser(String id) {
		PasswordResetTokenEntity resetTokenToSave = new PasswordResetTokenEntity(tokenGeneratorService.generateToken(id), new ObjectId(id));
		PasswordResetTokenEntity resetTokenSaved = passwordResetTokenRepository.save(resetTokenToSave);
		return passwordResetTokenEntityMapper.passwordResetTokenEntityToPasswordResetTokenDTO(resetTokenSaved);
	}
	
	@Override
	public Boolean isValid(String id, String token) {
		
		Boolean isValid = Boolean.TRUE;
		Calendar cal = Calendar.getInstance();
		PasswordResetTokenEntity passToken = passwordResetTokenRepository.findByToken(token);
		if (
				(passToken == null) || 
				(!passToken.getUser().equals(new ObjectId(id))) ||
				((passToken.getExpiryDate()
				        .getTime() - cal.getTime()
				        .getTime()) <= 0)
				) {
					isValid =  Boolean.FALSE;
		}
		
		return isValid;
	}
	
	@Override
	public PasswordResetTokenDTO getPasswordResetTokenForUser(String id) {
		return passwordResetTokenEntityMapper.passwordResetTokenEntityToPasswordResetTokenDTO(passwordResetTokenRepository.findByUser(new ObjectId(id)));
	}
	
	@Override
	public void deleteExpiredTokens() {
		passwordResetTokenRepository.deleteByExpiryDateBefore(new Date());
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(passwordResetTokenRepository, "Password Reset Token Repository can not be null");
		Assert.notNull(tokenGeneratorService, "Token Generator can not be null");
		Assert.notNull(passwordResetTokenEntityMapper, "Password Reset Token Entity Mapper can not be null");
	}

}
