package bakery.employeeTaskManager;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import bakery.employeeTaskManager.web.*;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
	@Autowired
	private AppUserDetailServiceImpl appuserDetailsService;

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		// Configuring HTTP security settings using lambda expressions
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(antMatcher("/css/**")).permitAll()
				// Permitting all requests to CSS resources, signup page, and save user endpoint
				.requestMatchers(antMatcher("/signup")).permitAll().requestMatchers(antMatcher("/saveuser")).permitAll()
				// Requiring authentication for all other requests
				.anyRequest().authenticated())
				.headers(headers -> headers.frameOptions(frameoptions -> frameoptions.disable() // for h2 console
				))
				// Setting custom login page and redirect on success
				.formLogin(formlogin -> formlogin.loginPage("/login").defaultSuccessUrl("/tasklist", true).permitAll())
				// Allowing everyone to access the logout functionality
				.logout(logout -> logout.permitAll());

		return http.build();
	}

	// Method to configure the global AuthenticationManager
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Setting up the userDetailsService with a password encoder
		auth.userDetailsService(appuserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
