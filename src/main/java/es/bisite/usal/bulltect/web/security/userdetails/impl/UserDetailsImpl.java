package es.bisite.usal.bulltect.web.security.userdetails.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import es.bisite.usal.bulltect.web.security.userdetails.CommonUserDetailsAware;

public class UserDetailsImpl<T> implements CommonUserDetailsAware<T> {

    private static Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);

    private static final long serialVersionUID = 1L;

    private T id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean locked;
    private Set<SimpleGrantedAuthority> grantedAuthorities;
    private Date lastPasswordResetDate;
    private Boolean active;
    private Date lastAccessToAlerts;
    private Date lastLoginAccess;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(T id, String email, String password, String firstName, String lastName,
            Boolean locked, Date lastPasswordResetDate, Boolean active,
            Set<SimpleGrantedAuthority> grantedAuthorities, Date lastAccessToAlerts, Date lastLoginAccess) {
        super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.locked = locked;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.active = active;
        this.grantedAuthorities = grantedAuthorities;
        this.lastAccessToAlerts = lastAccessToAlerts;
        this.lastLoginAccess = lastLoginAccess;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<SimpleGrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        logger.debug("Account is active -> " + active);
        return active;
    }

    @Override
    public T getUserId() {
        return id;
    }

    @Override
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
   
    @Override
	public Date getLastAccessToAlerts() {
		return lastAccessToAlerts;
	}
    
    @Override
	public Date getLastLoginAccess() {
		return lastLoginAccess;
	}

    @Override
    public String toString() {
        return "UserDetailsImpl [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + ", locked=" + locked + ", grantedAuthorities=" + grantedAuthorities
                + ", lastPasswordResetDate=" + lastPasswordResetDate + ", active=" + active + "]";
    }

	
}
