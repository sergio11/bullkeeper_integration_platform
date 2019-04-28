package sanchez.sanchez.sergio.bullkeeper.web.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import sanchez.sanchez.sergio.bullkeeper.exception.AuthenticationFailedException;
import sanchez.sanchez.sergio.bullkeeper.util.Utils;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error.CommonErrorRestController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Authentication Token Filter
 * @author ssanchez
 *
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @Autowired
    @Qualifier("UserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    private CommonErrorRestController commonErrorRestController;
    
    
    /**
     * Init Filter Bean
     */
    @Override
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		
		 //Manually get an instance of MyExceptionController
        final ApplicationContext ctx = WebApplicationContextUtils
                   .getRequiredWebApplicationContext(getServletContext());

        //MyExceptionHanlder is now accessible because I loaded it manually
        this.commonErrorRestController = ctx.getBean(CommonErrorRestController.class); 
	}

    /**
     * Do Filter Internal
     */
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

		try {
			populateSecurityContext(request);
		} catch (AuthenticationFailedException ex) {
			final ResponseEntity<APIResponse<String>> errorResponse =  commonErrorRestController.handleAuthenticationFailedException(ex);
		    Utils.populateResponse(errorResponse, response);
		    return;
		} catch (Throwable ex) {
			final ResponseEntity<APIResponse<String>> errorResponse = commonErrorRestController.handleException(ex, request, response);
			Utils.populateResponse(errorResponse, response);
		    return;
		}
       

        filterChain.doFilter(request, response);

    }

	/**
	 * Populate Security Context for current incoming request
	 * @param request
	 */
	private void populateSecurityContext(final HttpServletRequest request) {
		String authToken = request.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        logger.debug("checking authentication for user " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
            
                @SuppressWarnings("unchecked")
                CommonUserDetailsAware<Long> userDetails = (CommonUserDetailsAware<Long>) this.userDetailsService.loadUserByUsername(username);
                if (!userDetails.isPendingDelete() && userDetails.isEnabled() && 
                		userDetails.isAccountNonExpired()
                		&& jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (UsernameNotFoundException ex) {
                logger.debug("Username not found");
                throw new AuthenticationFailedException();
            }
            
        }
		
	}
	
}
