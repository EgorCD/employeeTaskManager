package bakery.employeeTaskManager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bakery.employeeTaskManager.domain.*;

/**
 * This class is used by spring security to authenticate and authorize user
 **/
@Service
public class AppUserDetailServiceImpl implements UserDetailsService {
	private final AppUserRepository repository;

	@Autowired
	public AppUserDetailServiceImpl(AppUserRepository userRepository) {
		this.repository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetching the user from the repository
		AppUser curruser = repository.findByUsername(username);
		// Creating UserDetails object with user information
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(),
				AuthorityUtils.createAuthorityList(curruser.getRole()));
		return user;
	}
}
