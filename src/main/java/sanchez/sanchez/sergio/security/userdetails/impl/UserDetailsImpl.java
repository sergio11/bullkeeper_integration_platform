package sanchez.sanchez.sergio.security.userdetails.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;

public class UserDetailsImpl<T> implements CommonUserDetailsAware<T> {

	private static final long serialVersionUID = 1L;
	
	private T id;
	private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<GrantedAuthority> grantedAuthorities;
    private Date lastPasswordResetDate;
    
    public UserDetailsImpl(){}

	public UserDetailsImpl(T id, String email, String password, String firstName, String lastName,
			Set<GrantedAuthority> grantedAuthorities) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.grantedAuthorities = grantedAuthorities;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setGrantedAuthorities(Set<GrantedAuthority> grantedAuthorities) {
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
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public T getUserId() {
		return id;
	}

	@Override
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
}
