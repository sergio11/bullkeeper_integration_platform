package sanchez.sanchez.sergio.security.handlers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.persistence.repository.UserSystemRepository;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.service.IAuthorizationService;

@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {
	
	private static Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);
    
    @Autowired
    private UserSystemRepository userSystemRepository;
    @Autowired
    private IAuthorizationService authorizationService;
    @Value("${redirect.after.success.login}")
    private String defaultSuccessUrl;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
    		throws ServletException, IOException {
        this.setDefaultTargetUrl(defaultSuccessUrl);
        logger.debug("LOGIN SUCCESS REDIRECT TO -> " + defaultSuccessUrl);
        CommonUserDetailsAware<ObjectId> userDetails = authorizationService.getUserDetails();
        userSystemRepository.updateLastLoginAccess(userDetails.getUserId(), new Date());
        HttpSession session = request.getSession();
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("url_prior_login");
            if (redirectUrl != null) {
                logger.debug("Redirigiendo usuario a : " + redirectUrl);
                // we do not forget to clean this attribute from session
                session.removeAttribute("url_prior_login");
                // then we redirect
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
